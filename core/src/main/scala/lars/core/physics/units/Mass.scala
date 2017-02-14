package lars.core.physics.units

import lars.core.math.Vector1

/**
  * Mass unit type.  Ensures correct use in physics equations.
  *
  * @param kg mass in kilograms
  */
case class Mass(kg: Double) extends Vector1[Mass] {
  override def +(that: Mass): Mass = Mass(kg + that.kg)
  override def -(that: Mass): Mass = Mass(kg - that.kg)
  override def *(scalar: Double): Mass = Mass(kg * scalar)
  override def /(scalar: Double): Mass = Mass(kg / scalar)
  override def /(that: Mass): Double = kg / that.kg

  override def unary_- : Mass = Mass(-kg)

  override def compare(that: Mass): Int = kg.compare(that.kg)

  override def midpoint(that: Mass): Mass = Mass(Vector1.midpoint(kg, that.kg))
  override def distance(that: Mass): Double = Vector1.distance(kg, that.kg)
  override def magnitude: Double = kg

  // Conversion to other types
  def /(that: Volume): Density = Density(kg / that.km3)

  def *(that: Velocity): Momentum = Momentum(that.ms * kg)
}

object Mass {
  val zero: Mass = Mass(0)

  def min(m1: Mass, m2: Mass): Mass =
    if(m1 < m2) m1 else m2

  def max(m1: Mass, m2: Mass): Mass =
    if(m1 > m2) m1 else m2

  /**
    * Returns a tuple with the larger Mass first.
    * @param m1 a mass
    * @param m2 another mass
    * @return tuple, larger Mass first
    */
  def sort(m1: Mass, m2: Mass): (Mass, Mass) =
    if(m1 > m2) (m1, m2) else (m2,m1)
}