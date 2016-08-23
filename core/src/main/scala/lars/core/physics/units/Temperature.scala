package lars.core.physics.units

import lars.core.math.Vector1

case class Temperature(k: Double) extends Vector1[Temperature] {
  override def +(that: Temperature): Temperature = new Temperature(k + that.k)
  override def -(that: Temperature): Temperature = new Temperature(k + that.k)
  override def *(scalar: Double): Temperature = new Temperature(k * scalar)
  override def /(scalar: Double): Temperature = new Temperature(k / scalar)
  override def /(that: Temperature): Double = k / that.k

  override def unary_- : Temperature = new Temperature(-k)

  override def compare(that: Temperature): Int = k.compare(that.k)

  override def midpoint(that: Temperature): Temperature = new Temperature(Vector1.midpoint(k, that.k))
  override def distance(that: Temperature): Double = Vector1.distance(k, that.k)
  override def magnitude: Double = k
}

object Temperature {
  val zero = new Temperature(0)
}