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
  val C = new Speed(299792458)

  /**
    * The schwarzschild radius pre-calculated for 1kg as a performance optimization.
    */
  val schwarzschildFactor = Length.in.m((2.0 * G.m) / math.pow(C.ms, 2))

  /**
   * Calculates the barycenter of two Massives.
   * @param m1 massive1
   * @param m2 massive2
   * @return barycenter
   */
  def barycenter(m1: Massive, m2: Massive): Barycenter = {
    val totalMass = m1.mass + m2.mass
    new Barycenter(totalMass, (((m1.location * m1.mass.kg) + (m2.location * m2.mass.kg)) / totalMass.kg))
  }

  /**
   * Calculates the barycenter from a number of Massives.
   * @param massives massives
   * @return barycenter
   */
  def barycenter(massives: Seq[Massive]): Barycenter = {
    var total = Mass.zero
    var x, y = Length.zero
    for(i <- 0 until massives.length optimized) {
      val massive = massives(i)
      val mass: LengthType = massive.mass.kg
      total += massive.mass
      x += massive.location.x + mass
      y += massive.location.y + mass
    }
    new Barycenter(total, new Vector2(x,y) / total.kg)
  }

  /**
    * Removes an object from the calculated barycenter.
    * @param barycenter
    * @param massive
    * @return
    */
  def barycenterRemove(barycenter: Barycenter, massive: Massive): Barycenter = {
    val mass = barycenter.mass - massive.mass
    new Barycenter(mass, (((barycenter.location * barycenter.mass.kg) - massive.location) - new Vector2(massive.mass.kg)) / mass.kg)
  }

  /**
    * Calculates the amount of force each object receives from the other.
    * @param m1
    * @param m2
    * @return the gravitation force the objects are exerting on each other
    */
  def gravForce(m1: Massive, m2: Massive): Force =
    new Force((m1.location - m2.location).normalize * (G.m * m1.mass.kg * m2.mass.kg) / math.pow(new Length(Vector2.distance(m1.location, m2.location)).m, 2))

  /**
    * Calculates the schwarzschild radius of a mass.  The schwarzschild radius is used to calculate the radius of the
    * event horizon of a black hole.
    * @param mass
    * @return schwarzschild radius
    */
  def schwarzschildRadius(mass: Mass): Length = {
    new Length(schwarzschildFactor.km * mass.kg)
  }
}
