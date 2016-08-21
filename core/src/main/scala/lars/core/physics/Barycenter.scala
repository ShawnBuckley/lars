package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vector2
import lars.core.physics.units.{Mass, Time, Velocity}

/**
  * This is a computed barycenter.  It extends Massive and can be used as such.
  * @param mass
  * @param location
  */
case class Barycenter(override var mass: Mass, override var location: Vector2) extends Massive {
  override var name: String = null
  override var velocity: Velocity = Velocity.zero
  override def observe(time: Time): Unit = {}
}
