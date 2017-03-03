lazy val name = "lars"
lazy val scalaV = "2.12.1"

lazy val server = (project in file("server")).settings(
  scalaVersion := scalaV,
  scalacOptions := Seq("-unchecked", "-deprecation"),
  libraryDependencies ++= Seq(
    "org.slf4j" % "log4j-over-slf4j" % "1.7.24",

    "io.dropwizard" % "dropwizard-core" % "1.1.0-rc1",

    "com.fasterxml.jackson" % "jackson-bom" % "2.8.7",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",

    "com.google.inject" % "guice" % "4.1.0",
    "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",
    "net.codingwell" %% "scala-guice" % "4.1.0",

    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
).dependsOn(commonJVM)

lazy val client = (project in file("client")).settings(
  scalaVersion := scalaV,
  scalacOptions := Seq("-unchecked", "-deprecation"),
  isScalaJSProject := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  )
).dependsOn(commonJS).
  enablePlugins(ScalaJSPlugin)

lazy val common = (crossProject.crossType(CrossType.Pure) in file ("common")).settings(
  version := "0.1-SNAPSHOT",
  scalaVersion := scalaV,
  scalacOptions := Seq("-unchecked", "-deprecation"),
  libraryDependencies ++= Seq(
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.0",

    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val commonJS = common.js
lazy val commonJVM = common.jvm

logBuffered in Test := false