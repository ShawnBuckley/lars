package model

case class Celestial(id: Option[Long],
                     kind: String,
                     name: Option[String],
                     x: Double,
                     y: Double,
                     mass: Double,
                     size: Option[Double],
                     velX: Option[Double],
                     velY: Option[Double],
                     observed: Option[Double],
                     parent: Option[Long],
                     ancestor: Option[Long]) {

  def setId(id: Long): Celestial = {
    Celestial(
      Some(id),
      kind,
      name,
      x,
      y,
      mass,
      size,
      velX,
      velY,
      observed,
      parent,
      ancestor
    )
  }
}

object Celestial {

}