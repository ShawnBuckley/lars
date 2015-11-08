package lars.game.engine.physics.units

import lars.game.engine.physics.units.Mass.MassType

case class Mass(mass: MassType) extends Ordered[Mass] {
  /**
    * Returns mass as the standard unit of measure, kg.
    * @return mass in kg
    */
  def kg: MassType = mass

  def +(that: Mass): Mass = new Mass(kg + that.kg)
  def +(that: MassType): Mass = new Mass(kg + that)

  override def >(that: Mass): Boolean = kg > that.kg
  override def <(that: Mass): Boolean = kg < that.kg
  override def >=(that: Mass): Boolean = kg >= that.kg
  override def <=(that: Mass): Boolean = kg <= that.kg

  override def compare(that: Mass): Int = (kg - that.kg).toInt
}

object Mass {
  type MassType = Double

  val zero: MassType = 0

  def min(m1: Mass, m2: Mass): Mass = if(m1 < m2) m1 else m2
  def max(m1: Mass, m2: Mass): Mass = if(m1 > m2) m1 else m2

  /**
    * Returns a tuple with the larger Mass first.
    * @param m1
    * @param m2
    * @return tuple, larger Mass first
    */
  def sort(m1: Mass, m2: Mass): (Mass, Mass) = {
    if(m1 > m2) (m1, m2) else (m2,m1)
  }
}