package lars.core.physics.units

import lars.core.math.{Vec2, Vector2}

case class Acceleration(ms2: Vec2) extends Vector2[Acceleration] {
  def +(that: Acceleration) = new Acceleration(ms2 + that.ms2)
  def -(that: Acceleration) = new Acceleration(ms2 - that.ms2)
  def *(scalar: Double) = new Acceleration(ms2 * scalar)
  def /(scalar: Double) = new Acceleration(ms2 / scalar)
  def /(that: Acceleration): Double = ms2 / that.ms2

  def unary_- = new Acceleration(-ms2)

  override def compare(that: Acceleration): Int = ms2.compare(that.ms2)

  def midpoint(that: Acceleration) = new Acceleration(ms2.midpoint(that.ms2))
  def distance(that: Acceleration) = new Acceleration(ms2.distance(that.ms2))
  def magnitude: Double = ms2.magnitude
  def normalize = new Acceleration(ms2.normalize)
  def angle: Double = ms2.angle

  def /(that: Time): Velocity =
    new Velocity(ms2 * that.s)
}

object Acceleration {
  val zero = Acceleration(Vec2.addIdent)
}