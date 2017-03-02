package lars.core.math

case class Polar2(angle: Double, length: Double) {
  def cartesian: Vec2 = {
    Vec2(length * Math.cos(angle), length * Math.sin(angle))
  }

  def cartesian(origin: Vec2): Vec2 = {
    Vec2(
      (length * Math.cos(angle)) + origin.x,
      (length * Math.sin(angle)) + origin.y)
  }
}

object Polar2 {
  /**
    * Converts a cartesian coordinate to a polar coordinate.
    *
    * @param point
    * @return polar coordinate
    */
  def convert(point: Vec2): Polar2 =
    new Polar2(point.angle, point.magnitude)

  /**
    * Converts a cartesian coordinate with a specified origin to a polar coordinate.
    *
    * @param origin
    * @param point
    * @return polar coordinate
    */
  def convert(origin: Vec2, point: Vec2): Polar2 =
    convert(point - origin)
}
