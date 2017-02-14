package lars.core.physics.units

import lars.core.math.Vector1

case class Mass(kg: Double) extends Vector1[Mass] {
  override def +(that: Mass): Mass = new Mass(kg + that.kg)
  override def -(that: Mass): Mass = new Mass(kg - that.kg)
  override def *(scalar: Double): Mass = new Mass(kg * scalar)
  override def /(scalar: Double): Mass = new Mass(kg / scalar)
  override def /(that: Mass): Double = kg / that.kg

  override def unary_- : Mass = new Mass(-kg)

  override def compare(that: Mass): Int = kg.compare(that.kg)

  override def midpoint(that: Mass): Mass = new Mass(Vector1.midpoint(kg, that.kg))
  override def distance(that: Mass): Double = Vector1.distance(kg, that.kg)
  override def magnitude: Double = kg

  // Conversion to other types

  def /(that: Volume): Density = new Density(kg / that.km3)

  def *(that: Velocity): Momentum = new Momentum(that.ms * kg)
}

object Mass {
  val zero: Mass = new Mass(0)

  def min(m1: Mass, m2: Mass): Mass =
    if(m1 < m2) m1 else m2

  def max(m1: Mass, m2: Mass): Mass =
    if(m1 > m2) m1 else m2

  /**
    * Returns a tuple with the larger Mass first.
    * @param m1
    * @param m2
    * @return tuple, larger Mass first
    */
  def sort(m1: Mass, m2: Mass): (Mass, Mass) =
    if(m1 > m2) (m1, m2) else (m2,m1)
}