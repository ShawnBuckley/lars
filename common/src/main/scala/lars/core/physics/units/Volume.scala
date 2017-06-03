package lars.core.physics.units

import lars.core.math.Vector1

case class Volume(km3: Double) extends Vector1[Volume] {
  override def +(that: Volume): Volume = Volume(km3 + that.km3)
  override def -(that: Volume): Volume = Volume(km3 - that.km3)
  override def *(scalar: Double): Volume = Volume(km3 * scalar)
  override def /(scalar: Double): Volume = Volume(km3 * scalar)
  override def /(that: Volume): Double = km3 / that.km3

  override def unary_- : Volume = Volume(-km3)

  override def compare(that: Volume): Int = km3.compareTo(that.km3)

  override def midpoint(that: Volume): Volume = Volume(Vector1.midpoint(km3, that.km3))
  override def distance(that: Volume): Double = Vector1.distance(km3, that.km3)
  override def magnitude: Double = km3
}