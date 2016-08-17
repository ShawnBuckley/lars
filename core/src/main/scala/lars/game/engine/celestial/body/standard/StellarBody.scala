package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.{Child, Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Velocity, Length, Mass}

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
                  override var location: Vector2,
                  override var velocity: Velocity,
                  override var size: Length,
                  override var parent: Parent) extends Sizeable with Child {
  override def observe(): Unit = {}

  override def collide(other: Sizeable): Unit = ???
}
