sbt-aspectj
===========

This is a fork of [sbt-aspectj](https://github.com/sbt/sbt-aspectj). The fork was made because it appears that lightbend
is no longer maintaining/working on the plugin. The organization of the plugin has been changed to `org.mdedetrich` and
the package has been changed to `org.mdedetrich.sbt` however the artifact name is exactly the same.

[sbt] plugin for weaving with [aspectj]. This plugin requires sbt 0.13.5+.

Add plugin
----------

Add plugin to `project/plugins.sbt`. For example:

    addSbtPlugin("org.mdedetrich" % "sbt-aspectj" % "{version}")

See [released versions][releases].

Sample projects
---------------

There are [runnable sample projects][samples] included as sbt scripted tests.

License
-------

This code is open source software licensed under the [Apache 2.0 License][apache]. Feel free to use it accordingly.


[sbt]: https://github.com/sbt/sbt
[aspectj]: http://www.eclipse.org/aspectj
[releases]: https://github.com/mdedetrich/sbt-aspectj/releases
[samples]: https://github.com/mdedetrich/sbt-aspectj/tree/master/src/sbt-test
[cla]: https://www.lightbend.com/contribute/cla
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html
