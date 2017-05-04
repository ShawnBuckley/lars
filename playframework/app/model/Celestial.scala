package model

case class Celestial(var id: Option[Long],
                     kind: String,
                     name: Option[String],
                     x: Double,
                     y: Double,
                     mass: Double,
                     size: Option[Double],
                     velX: Double,
                     velY: Double,
                     lastObserved: Option[Double],
                     parent: Long,
                     ancestor: Option[Long])