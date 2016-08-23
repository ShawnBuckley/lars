package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}

/**
  * This is a computed barycenter.  It extends Massive and can be used as such.
  * @param mass
  * @param location
  */
case class Barycenter(override var mass: Mass, override var location: Vec2) extends Massive {
  override var velocity: Velocity = Velocity.zero
}
