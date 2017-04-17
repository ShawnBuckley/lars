val name = "lars"
val scalaV = "2.11.8"

lazy val server = (project in file("server")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := Seq(client),
  scalacOptions := Seq("-unchecked", "-deprecation"),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "org.slf4j" % "log4j-over-slf4j" % "1.7.24",
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.fasterxml.jackson" % "jackson-bom" % "2.8.7",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",

    "org.webjars.npm" % "bootstrap" % "3.3.7",

    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
).enablePlugins(PlayScala).
  dependsOn(commonJVM)

lazy val client = (project in file("client")).settings(
  scalaVersion := scalaV,
  scalacOptions := Seq("-unchecked", "-deprecation"),
  isScalaJSProject := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  ),
  jsDependencies ++= Seq(
    "org.webjars.npm" % "jquery" % "3.2.1" / "dist/jquery.js",
    "org.webjars.npm" % "bootstrap" % "3.3.7" / "bootstrap.js" dependsOn "dist/jquery.js",
    "org.webjars.npm" % "angular" % "1.6.4" / "angular.js" dependsOn "dist/jquery.js"
  )
).dependsOn(commonJS).
  enablePlugins(ScalaJSPlugin, ScalaJSWeb)

lazy val common = (crossProject.crossType(CrossType.Pure) in file("common")).settings(
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
