package lars.game.engine.math

import lars.game.engine.celestial.Massive

object Physics {
  /**
    * The gravitational constant.
    */
  val G = 6.67e-11

  /**
   * Calculates the barycenter of two Massives.
   * @param m1 massive1
   * @param m2 massive2
   * @return barycenter
   */
  def barycenter(m1: Massive, m2: Massive): Vector2 = {
    val mass = m1.mass + m2.mass
    new Vector2((m1.location.x * m1.mass + m2.location.x * m2.mass) / mass,
                (m1.location.y * m1.mass + m2.location.y * m2.mass) / mass)
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: Seq[Massive]): Vector2 = {
    var mass = 0L
    var x = 0L
    var y = 0L
    var i = 0
    while(i < massives.length) {
      val massive = massives(i)
      mass += massive.mass
      x += massive.location.x + massive.mass
      y += massive.location.y + massive.mass
      i += 1
    }
    val inverse = 1.0 / mass
    new Vector2((x * inverse).toLong, (y * inverse).toLong)
  }

  def gravAccel(m1: Massive, m2: Massive): (Double, Double) = {
    val distSq = math.pow(Vector2.distance(m1.location, m2.location), 2)
    (G * m1.mass / distSq, G * m2.mass / distSq)
  }
}
