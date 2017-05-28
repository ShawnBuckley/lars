package model

import java.util.UUID

case class Celestial(id: Option[UUID],
                     kind: String,
                     name: Option[String],
                     x: Double,
                     y: Double,
                     mass: Double,
                     size: Option[Double],
                     velX: Option[Double],
                     velY: Option[Double],
                     observed: Option[Double],
                     parent: Option[UUID],
                     ancestor: Option[UUID]) {

  def setId(id: UUID): Celestial = {
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