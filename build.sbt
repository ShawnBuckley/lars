//enablePlugins(ScalaJSPlugin)
name := "Logistics and Research Simulator"
scalaVersion in ThisBuild := "2.11.8"
lazy val root = project.in(file(".")).
  aggregate().
  settings(
    publish := {},
    publishLocal := {}
  )
lazy val project = crossProject.in(file(".")).
  settings(
    name := "lars",
    version := "0.1-SNAPSHOT"
  ).
  jvmSettings(
    libraryDependencies ++= Seq(
      "io.dropwizard" % "dropwizard-core" % "1.0.0",

      "com.fasterxml.jackson" % "jackson-parent" % "2.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",

      "com.google.inject" % "guice" % "4.1.0",
      "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",

      "javax.websocket" % "javax.websocket-api" % "1.1",
      "com.corundumstudio.socketio" % "netty-socketio" % "1.7.11",

      "org.testng" % "testng" % "6.9.10" % "test"
    )
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.0"
    )
  )
lazy val client = project.js
lazy val server = project.jvm