package lars.game.engine.math

import lars.game.engine.physics.units.Length.LengthType

case class Polar2(angle: Double, length: LengthType) {

}

object Polar2 {
  /**
    * Converts a cartesian coordinate to a polar coordinate.
    *
    * @param point
    * @return polar coordinate
    */
  def convert(point: Vector2): Polar2 =
    new Polar2(point.angle, point.length)

  /**
    * Converts a cartesian coordinate with a specified origin to a polar coordinate.
    *
    * @param origin
    * @param point
    * @return polar coordinate
    */
  def convert(origin: Vector2, point: Vector2): Polar2 =
    convert(point - origin)
}
