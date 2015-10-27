package lars.game.engine.math

import lars.game.engine.celestial.Massive

import scala.collection.mutable.ArrayBuffer

object Physics {
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
    val dist = distance(m1.location, m2.location)
    val center = center(m1.location, m2.location)
    val massRatio = massRatio(m1, m2)

    if(m1.mass > m2.mass) {

    } else {

    }
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: ArrayBuffer[Massive]): Vector2 = {

  }
}
