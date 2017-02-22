package lars.core.physics.units

import lars.core.math.Vector1

case class Pressure(Nm2: Double) extends Vector1[Pressure] {
  override def +(that: Pressure): Pressure = new Pressure(Nm2 + that.Nm2)
  override def -(that: Pressure): Pressure = new Pressure(Nm2 - that.Nm2)
  override def *(scalar: Double): Pressure = new Pressure(Nm2 * scalar)
  override def /(scalar: Double): Pressure = new Pressure(Nm2 / scalar)
  override def /(that: Pressure): Double = Nm2 / that.Nm2

  override def unary_- : Pressure = new Pressure(-Nm2)

  override def compare(that: Pressure): Int = Nm2.compare(that.Nm2)

  override def midpoint(that: Pressure): Pressure = new Pressure(Vector1.midpoint(Nm2, that.Nm2))
  override def distance(that: Pressure): Double = Vector1.distance(Nm2, that.Nm2)
  override def magnitude: Double = Nm2
}

object Pressure {
  val zero = new Pressure(0)
}