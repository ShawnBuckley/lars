package lars.core.physics.units

import lars.core.math.Vector1

case class Density(kgm3: Double) extends Vector1[Density] {
  override def +(that: Density): Density = new Density(kgm3 + that.kgm3)
  override def -(that: Density): Density = new Density(kgm3 + that.kgm3)
  override def *(scalar: Double): Density = new Density(kgm3 * scalar)
  override def /(scalar: Double): Density = new Density(kgm3 / scalar)
  override def /(that: Density): Double = kgm3 / that.kgm3

  override def unary_- : Density = new Density(-kgm3)

  override def compare(that: Density): Int = kgm3.compare(that.kgm3)

  override def midpoint(that: Density): Density = new Density(Vector1.midpoint(kgm3, that.kgm3))
  override def distance(that: Density): Double = Vector1.distance(kgm3, that.kgm3)
  override def magnitude: Double = kgm3
}

object Density {
  val zero = new Density(0)
}
