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

  def add(massive: Massive): Unit = {
    mass += massive.mass
    location = ((location * mass.kg) + (massive.location * massive.mass.kg)) / mass.kg
  }
}
