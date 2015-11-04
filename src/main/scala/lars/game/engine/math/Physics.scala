package lars.game.engine.math

import lars.game.engine.celestial.Massive
import lars.game.engine.celestial.body.{MassiveBody, TerrestrialBody}

object Physics {
  val G = 6.67e-11

  /**
   * Calculates the barycenter of two Massives.
   * @param m1 massive1
   * @param m2 massive2
   * @return barycenter
   */
  def barycenter(m1: Massive, m2: Massive): Vector2 = {
    if(m1.mass > m2.mass) {
      barycenterSorted(m1, m2)
    } else if(m1.mass < m2.mass) {
      barycenterSorted(m2, m1)
    } else {
      Vector2.midpoint(m1.location, m2.location)
    }
  }

  private def barycenterSorted(larger: Massive, smaller: Massive): Vector2 = {
    val massRatio = smaller.mass/larger.mass.toDouble
    val xDist = (larger.location.x - smaller.location.x) * massRatio
    val yDist = (larger.location.y - smaller.location.y) * massRatio
    new Vector2(math.round(larger.location.x - xDist), math.round(larger.location.y - yDist))
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
