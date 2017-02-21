//enablePlugins(ScalaJSPlugin)
name := "Logistics and Research Simulator"
scalaVersion in ThisBuild := "2.12.1"
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
      "io.dropwizard" % "dropwizard-core" % "1.1.0-rc1",

      "com.fasterxml.jackson" % "jackson-bom" % "2.8.7",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",

      "com.google.inject" % "guice" % "4.1.0",
      "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",

      "javax.websocket" % "javax.websocket-api" % "1.1",
      "com.corundumstudio.socketio" % "netty-socketio" % "1.7.12",

      "org.testng" % "testng" % "6.10" % "test"
    )
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.lihaoyi" %%% "upickle" % "0.4.4",
      "io.scalajs.npm" %%% "socket-io-client" % "0.3.0.3",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.0"
    )
  )
lazy val client = project.js
lazy val server = project.jvm