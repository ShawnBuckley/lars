package lars.game.engine.physics

case class Mass(mass: Long) extends Ordered[Mass] {
  /**
    * Returns mass as the standard unit of measure, kg
    * @return mass in kg
    */
  def kg: Long = mass

  def +(that: Mass): Mass = new Mass(kg + that.kg)
  def +(that: Long): Mass = new Mass(kg + that)

  override def >(that: Mass): Boolean = kg > that.kg
  override def <(that: Mass): Boolean = kg < that.kg
  override def >=(that: Mass): Boolean = kg >= that.kg
  override def <=(that: Mass): Boolean = kg <= that.kg

  override def compare(that: Mass): Int = (kg - that.kg).toInt
}

object Mass {
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