package lars.core.celestial.body.standard

import lars.core.celestial.{Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

/**
  * Stellar bodies are stars of all varieties.  This includes stars, neutron stars, and, pulsars.
  *
  * See: https://docs.google.com/document/d/1XPz32qaK-0D5I7oc1eLFulBTn2nzxTo0b6mf65V0RLQ
  *
  * @param mass
  * @param location
  * @param size
  */
class StellarBody(override var name: String,
                  override var mass: Mass,
                  override var location: Vec2,
                  override var velocity: Velocity,
                  override var size: Length,
                  override var parent: Parent)
  extends StandardBody {

  override def collide(other: Sizeable): Unit = ???
}
