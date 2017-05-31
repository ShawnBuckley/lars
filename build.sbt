val name = "lars"

lazy val settings = Seq(
  organization := "lars.game",
  version := "0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "feature", "-Xlint"),
  publish := {},
  publishLocal := {}
)

lazy val playframework = (project in file("playframework"))
  .settings(settings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j" % "1.7.25",
      "net.codingwell" %% "scala-guice" % "4.1.0",

      "com.typesafe.play" %% "play-slick" % "2.1.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "2.1.0",
      "org.postgresql" % "postgresql" % "42.1.1",

      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "test",
      "org.scalatest" %% "scalatest" % "3.0.3" % "test"
    )
  )
  .enablePlugins(PlayScala)
  .dependsOn(common)

lazy val common = (project in file("common"))
  .settings(settings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.3" % "test"
    )
  )

logBuffered in Test := false
