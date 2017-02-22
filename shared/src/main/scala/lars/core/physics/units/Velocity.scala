package lars.core.physics.units

import lars.core.math.{Vec2, Vector2}

case class Velocity(ms: Vec2) extends Vector2[Velocity] {
  def +(that: Velocity) = new Velocity(ms + that.ms)
  def -(that: Velocity) = new Velocity(ms + that.ms)
  def *(scalar: Double) = new Velocity(ms * scalar)
  def /(scalar: Double) = new Velocity(ms / scalar)
  def /(that: Velocity): Double  = ms / that.ms

  def unary_- = new Velocity(-ms)

  override def compare(that: Velocity): Int = ms.compare(that.ms)

  def midpoint(that: Velocity) = new Velocity(ms.midpoint(that.ms))
  def distance(that: Velocity) = new Velocity(ms.distance(that.ms))
  def magnitude: Double = ms.magnitude
  def normalize = new Velocity(ms.normalize)
  def angle: Double = ms.angle
  
  def kms: Vec2 = ms / 1000

  // Conversions

  def speed: Speed =
    new Speed(magnitude)

  def *(time: Time): Vec2 =
    (ms * time.s) / 1000
}

object Velocity {
  val zero = new Velocity(Vec2.addIdent)
}