package lars.core.physics.units

import lars.core.math.Vector1

case class Area(km2: Double) extends Vector1[Area] {
  override def +(that: Area): Area = new Area(km2 + that.km2)
  override def -(that: Area): Area = new Area(km2 + that.km2)
  override def *(scalar: Double): Area = new Area(km2 * scalar)
  override def /(scalar: Double): Area = new Area(km2 / scalar)
  override def /(that: Area): Double = km2 / that.km2

  override def unary_- : Area = new Area(-km2)

  override def compare(that: Area): Int = km2.compare(that.km2)

  override def midpoint(that: Area): Area = new Area(Vector1.midpoint(km2, that.km2))
  override def distance(that: Area): Double = Vector1.distance(km2, that.km2)
  override def magnitude: Double = km2


  def m2: Double =
    km2 * 1000

  def *(that: Length): Volume =
    new Volume(km2 * that.km)

  def *(that: Force): Pressure =
    new Pressure(m2 * that.N.magnitude)
}