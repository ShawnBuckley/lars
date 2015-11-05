package lars.game.engine.math

import lars.game.engine.celestial.Massive
import lars.game.engine.celestial.body.{MassiveBody, TerrestrialBody}

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
    val inverse = 1 / mass
    new Vector2((m1.location.x * mass + m2.location.x * mass) * inverse, (m1.location.y * mass + m2.location.y * mass) * inverse)
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: Seq[Massive]): Vector2 = massives.length match {
    case 0 => Vector2.addIdent
    case 1 => massives(0).location
    case 2 => barycenter(massives(0), massives(1))
    case _ => {
      var center = new MassiveBody(massives(0).mass + massives(1).mass, barycenter(massives(0), massives(1)))
      var i = 2
      while(i < massives.length) {
        center = new MassiveBody(center.mass + massives(i).mass, barycenter(center, massives(i)))
        i += 1
      }
      center.location
    }
  }

  def gravAccel(m1: Massive, m2: Massive): (Double, Double) = {
    val distSq = math.pow(Vector2.distance(m1.location, m2.location), 2)
    (G * m1.mass / distSq, G * m2.mass / distSq)
  }
}
