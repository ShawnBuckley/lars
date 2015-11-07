package lars.game.engine.physics

case class Mass(mass: Long) {
  /**
    * Returns mass as the standard unit of measure, kg
    * @return mass in kg
    */
  def kg: Long = mass

  def +(that: Mass): Mass = new Mass(kg + that.kg)
  def +(that: Long): Mass = new Mass(kg + that)
}
