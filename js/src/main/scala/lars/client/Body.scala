package lars.client

import lars.core.Nameable
import lars.core.celestial.Sizeable
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

case class Body(override var name: Option[String],
           override var mass: Mass,
           override var location: Vec2,
           override var velocity: Velocity,
           override var size: Length
          ) extends Sizeable with Nameable {
  // Not implemented in the client
  override def collide(other: Sizeable): Unit = ???
}
