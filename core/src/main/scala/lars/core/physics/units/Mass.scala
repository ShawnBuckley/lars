package lars.core.physics.units

import lars.core.physics.units.Mass.MassType

case class Mass(kg: MassType) extends Ordered[Mass] {
  def +(that: Mass): Mass =
    new Mass(kg + that.kg)

  def +(that: MassType): Mass =
    new Mass(kg + that)

  def -(that: Mass): Mass =
    new Mass(kg - that.kg)

  def -(that: MassType): Mass =
    new Mass(kg - that)

  def *(factor: MassType): Mass =
    new Mass(kg * factor)

  override def >(that: Mass): Boolean =
    kg > that.kg

  override def <(that: Mass): Boolean =
    kg < that.kg

  override def >=(that: Mass): Boolean =
    kg >= that.kg

  override def <=(that: Mass): Boolean =
    kg <= that.kg

  override def compare(that: Mass): Int =
    (kg - that.kg).toInt

  // Conversion to other types

  def /(that: Volume): Density =
    new Density(kg / that.km3)

  def *(that: Velocity): Momentum =
    new Momentum(that.ms * kg)
}

object Mass {
  type MassType = Double

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