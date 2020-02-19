import org.mdedetrich.sbt.SbtAspectj.aspectjUseInstrumentedClasses

organization := "org.mdedetrich.sbt.aspectj"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.10"

enablePlugins(SbtAspectj)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.16"

// add akka-actor as an aspectj input (find it in the update report)
aspectjInputs in Aspectj ++= update.value.matching(
  moduleFilter(organization = "com.typesafe.akka", name = "akka-actor*"))

// replace the original akka-actor jar with the instrumented classes in runtime
fullClasspath in Runtime := aspectjUseInstrumentedClasses(Runtime).value

// for sbt scripted test:
TaskKey[Unit]("check") := {
  import scala.sys.process.Process

  val cp   = (fullClasspath in Runtime).value
  val mc   = (mainClass in Runtime).value
  val opts = (javaOptions in run in Compile).value

  val expected = "Actor asked world\nhello world\n"
  val output   = Process("java", opts ++ Seq("-classpath", cp.files.absString, mc getOrElse "")).!!
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
