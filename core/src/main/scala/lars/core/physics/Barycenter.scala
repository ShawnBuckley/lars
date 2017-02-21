package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}

/**
  * This is a computed barycenter.  It extends Massive and can be used as such.
  * @param mass total mass of the barycenter
  * @param location calculated center of mass
  */
case class Barycenter(override var mass: Mass, override var location: Vec2) extends Massive {
  override var velocity: Velocity = Velocity.zero

  /**
    * Recalculates the barycenter to include another massive object.
    * @param massive massive to add
    */
  def add(massive: Massive): Unit = {
    val totalMass = mass + massive.mass
    location = new Vec2(
      (mass*location.x + massive.mass*massive.location.x) / totalMass,
      (mass*location.y + massive.mass*massive.location.y) / totalMass
    )
    mass = totalMass
  }

  /**
    * Recalculates the barycenter to include a set of massive objects.
    * @param massives massives to add
    */
  def add(massives: Seq[Massive]): Unit = {
    var total = Mass.zero
    var newLocation = Vec2.addIdent
    massives.foreach((massive: Massive) => {
      total += massive.mass
      newLocation += (massive.location * massive.mass.kg)
    })
    mass += total
    location += (newLocation / total.kg)
  }
}

object Barycenter {
  /**
    * Calculates the barycenter of two massives.
    * @param m1 massive1
    * @param m2 massive2
    * @return barycenter
    */
  def calculate(m1: Massive, m2: Massive): Barycenter = {
    val totalMass = m1.mass + m2.mass
    Barycenter(totalMass, ((m1.location * m1.mass.kg) + (m2.location * m2.mass.kg)) / totalMass.kg)
  }

  /**
    * Calculates the barycenter from a number of massives.
    * @param massives massives
    * @return barycenter
    */
  def calculate(massives: Seq[Massive]): Barycenter = {
    var total = Mass.zero
    var location = Vec2.addIdent
    massives.foreach((massive: Massive) => {
      total += massive.mass
      location += (massive.location * massive.mass.kg)
    })
    Barycenter(total, location / total.kg)
  }

  /**
    * Removes an object from the calculated barycenter.
    * WARNING! This can be used to remove a mass that was not added to the barycenter originally!
    * @param barycenter calculated barycenter
    * @param massive massive object to remove
    * @return barycenter with mass removed
    */
  def remove(barycenter: Barycenter, massive: Massive): Barycenter = {
    val massRemoved = barycenter.mass - massive.mass
    Barycenter(massRemoved, ((barycenter.location - ((massive.location * massive.mass.kg) / barycenter.mass.kg)) * barycenter.mass.kg) / massRemoved.kg)
  }
}