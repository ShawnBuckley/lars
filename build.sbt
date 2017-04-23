val name = "lars"

lazy val settings = Seq(
  organization := "lars.game",
  version := "0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "feature", "-Xlint"),
  publish := {},
  publishLocal := {}
)

lazy val server = (project in file("server"))
  .settings(settings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j" % "1.7.24",
      "net.codingwell" %% "scala-guice" % "4.1.0",
      "com.fasterxml.jackson" % "jackson-bom" % "2.8.7",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",

      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
  .enablePlugins(PlayScala)
  .dependsOn(common)

lazy val common = (project in file("common"))
  .settings(settings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.0",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )

logBuffered in Test := false
