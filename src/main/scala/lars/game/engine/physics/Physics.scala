package lars.game.engine.physics

import lars.game.engine.physics.units.Length.LengthType

import scalaxy.loops._
import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Force, Speed, Length, Mass}

object Physics {
  /**
    * The gravitational constant.
    */
  // TODO - use correct unit for this (Nm2/kg2)
  val G = new Length(6.67e-14)

  /**
    * Speed of light in a vacuum.
    */
  val C = new Speed(299792.458)

  /**
    * The proportionality constant (for 1kg), a shortcut for calculating the schwarzschild radius.
    */
  // TODO - correct units to not need division by 1e9.
  val propConst = new Length((2.0 * G.m) / math.pow(C.KmS, 2) / 1e9)

  /**
   * Calculates the barycenter of two Massives.
   * @param m1 massive1
   * @param m2 massive2
   * @return barycenter
   */
  def barycenter(m1: Massive, m2: Massive): Vector2 = {
    val inverse = 1 / (m1.mass.kg + m2.mass.kg)
    new Vector2((m1.location.x * m1.mass.kg + m2.location.x * m2.mass.kg) * inverse,
               ((m1.location.y * m1.mass.kg + m2.location.y * m2.mass.kg) * inverse))
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: Seq[Massive]): Vector2 = {
    var total = new Mass(Mass.zero)
    var x, y = Length.zero
    for(i <- 0 until massives.length optimized) {
      val massive = massives(i)
      val mass: LengthType = massive.mass.kg
      val loc = massive.location
      total += massive.mass
      x += loc.x + mass
      y += loc.y + mass
    }
    val inverse = 1.0 / total.kg
    new Vector2(x * inverse, y * inverse)
  }

  /**
    * Calculates the amount of force each object receives from the other.
    * @param m1
    * @param m2
    * @return tuple, force each object receives.
    */
  def gravForce(m1: Massive, m2: Massive): Force = {
    new Force((G.m * m1.mass.kg * m2.mass.kg) / math.pow(new Length(Vector2.distance(m1.location, m2.location)).m, 2))
  }

  /**
    * Calculates the drift offset of two objects due to gravity.
    * @param m1
    * @param m2
    * @return tuple, gravity acceleration
    */
  def gravAccel(m1: Massive, m2: Massive): (Vector2, Vector2) = {
    (null,null)
//    val forces = gravForce(m1, m2)
//    (m1.location * forces._1, m2.location * forces._2)
  }

  def schwarzschildRadius(mass: Mass): Length = {
    new Length(propConst.km * mass.kg)
  }
}
