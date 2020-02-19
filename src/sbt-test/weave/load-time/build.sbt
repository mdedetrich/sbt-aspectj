lazy val buildSettings = Seq(
  organization := "org.mdedetrich.sbt.aspectj",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.10"
)

lazy val sample = (project in file("."))
  .settings(buildSettings)
  .aggregate(inputs, tracer)

lazy val inputs = (project in file("inputs"))
  .settings(buildSettings)

lazy val tracer = (project in file("tracer"))
  .enablePlugins(SbtAspectj)
  .settings(buildSettings)
  .settings(
    // only compile the aspects (no weaving)
    aspectjCompileOnly in Aspectj := true,

    // add the compiled aspects as products
    products in Compile ++= (products in Aspectj).value
  )
  .dependsOn(inputs)

lazy val woven = (project in file("woven"))
  .enablePlugins(SbtAspectj)
  .settings(buildSettings)
  .settings(
    // fork the run so that javaagent option can be added
    fork in run := true,

    // add the aspectj weaver javaagent option
    javaOptions in run ++= (aspectjWeaverOptions in Aspectj).value
  ).dependsOn(inputs, tracer)

// for sbt scripted test:
TaskKey[Unit]("check") := {
  import scala.sys.process.Process

  val cp = (fullClasspath in Compile in woven).value
  val mc = (mainClass in Compile in woven).value
  val opts = (javaOptions in run in Compile in woven).value

  val expected = "Printing sample:\nhello\n"
  val output = Process("java", opts ++ Seq("-classpath", cp.files.absString, mc getOrElse "")).!!
  if (output != expected) {
    println("Unexpected output:")
    println(output)
    println("Expected:")
    println(expected)
    sys.error("Unexpected output")
  } else {
    print(output)
  }
}
