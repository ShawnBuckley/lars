package lars.game.engine.physics

import scalaxy.loops._

import lars.game.engine.Types
import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Length, Mass}

object Physics {
  /**
    * The gravitational constant.
    */
  val G = 6.67e-11

  /**
    * Speed of light in a vacuum.
    */
  val C = 299792458

  /**
   * Calculates the barycenter of two Massives.
   * @param m1 massive1
   * @param m2 massive2
   * @return barycenter
   */
  def barycenter(m1: Massive, m2: Massive): Vector2 = {
    val inverse = 1 / (m1.mass.kg + m2.mass.kg)
    new Vector2(Types.toDistance((m1.location.x * m1.mass.kg + m2.location.x * m2.mass.kg) * inverse),
                Types.toDistance((m1.location.y * m1.mass.kg + m2.location.y * m2.mass.kg) * inverse))
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: Seq[Massive]): Vector2 = {
    var total = new Mass(Types.zeroMass)
    var x, y = Types.zeroDistance
    for(i <- 0 until massives.length optimized) {
      val massive = massives(i)
      val mass = Types.toDistance(massive.mass.kg)
      val loc = massive.location
      total += massive.mass
      x += loc.x + mass
      y += loc.y + mass
    }
    val inverse = 1.0 / total.kg
    new Vector2(Types.toDistance(x * inverse), Types.toDistance(y * inverse))
  }

  /**
    * Calculates the amount of force each object receives from the other.
    * @param m1
    * @param m2
    * @return tuple, force each object receives.
    */
  def gravForce(m1: Massive, m2: Massive): (Double, Double) = {
    val distSq = math.pow(Vector2.distance(m1.location, m2.location), 2)
    (G * m1.mass.kg / distSq, G * m2.mass.kg / distSq)
  }

  /**
    * Calculates the drift offset of two objects due to gravity.
    * @param m1
    * @param m2
    * @return tuple, gravity acceleration
    */
  def gravAccel(m1: Massive, m2: Massive): (Vector2, Vector2) = {
    val forces = gravForce(m1, m2)
    (m1.location * forces._1, m2.location * forces._2)
  }

  def schwarzschildRadius(mass: Mass): Length = {
    new Length(Types.toDistance(2 * G * mass.kg) / C)

  }
}
