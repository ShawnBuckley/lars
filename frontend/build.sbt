import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "Lars Frontend"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "scalatags" % "0.5.4",

  "org.webjars" % "bootstrap" % "3.3.7-1",
  "org.webjars.bower" % "jquery" % "3.1.0",
  "org.webjars.bower" % "jquery-mousewheel" % "3.1.13",
  "org.webjars.bower" % "socket.io-client" % "1.4.5"
)

jsDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "3.3.7-1" / "bootstrap.js",
  "org.webjars.bower" % "jquery" % "3.1.0" / "dist/jquery.js",
  "org.webjars.bower" % "jquery-mousewheel" % "3.1.13" / "jquery.mousewheel.js" dependsOn "dist/jquery.js",
  "org.webjars.bower" % "socket.io-client" % "1.4.5" / "socket.io.js"
)

bootSnippet := "example.ScalaJSExample().main(document.getElementById('canvas'));"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
