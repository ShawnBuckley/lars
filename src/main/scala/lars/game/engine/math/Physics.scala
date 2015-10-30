package lars.game.engine.math

import lars.game.engine.celestial.Massive
import lars.game.engine.celestial.body.{MassiveBody, TerrestrialBody}

object Physics {
  val G = 6.67e-11
  /**
   * Returns the midpoint between two points.
   * @param p1 point1
   * @param p2 point2
   * @return midpoint
   */
  def midpoint(p1: Vector2, p2: Vector2): Vector2 = {
    new Vector2((p1.x + p2.x)/2, (p1.y + p2.y)/2)
  }

  /**
   * Returns the distance between two points.
   * @param p1 point1
   * @param p2 point2
   * @return distance
   */
  def distance(p1: Vector2, p2: Vector2): Long = {
    math.sqrt(math.pow(p2.x - p1.x, 2) + math.pow(p2.y - p2.x, 2)).toLong
  }

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
      midpoint(m1.location, m2.location)
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
    case 0 => Vector2.identity
    case 1 => massives(0).location
    case 2 => barycenter(massives(0), massives(1))
    case _ => {
      var center = new MassiveBody(
        massives(0).mass + massives(1).mass,
        barycenter(massives(0), massives(1)),
        Vector2.identity
      )
      var i = 2
      while(i < massives.length) {
        center = new MassiveBody(
          center.mass + massives(i).mass,
          barycenter(center, massives(i)),
          Vector2.identity
        )
        i += 1
      }
      center.location
    }
  }

//  def gravAccel(m1: Massive, m2: Massive): (Double, Double) = {
//    val dist = distance(m1.location, m2.location)
//    val f =
//      (
//        )
//  }

  def main(args: Array[String]): Unit = {
//     TerrestrialBody(mass: Long, loc: Vector2, orbit: (Double) => Vector2, size: Long, primary: Massive, dist: Long, drift: Vector2)
    val m1 = new TerrestrialBody(1000000, Vector2(10,0), (Double) => new Vector2(0,0), 1000, null, 1000, new Vector2(0,0))
    val m2 = new TerrestrialBody(1000, Vector2(0,0), (Double) => new Vector2(0,0), 1000, null, 1000, new Vector2(0,0))
    val barycenter = Physics.barycenter(m2, m1)
    print(barycenter.x + "," + barycenter.y)
  }
}
