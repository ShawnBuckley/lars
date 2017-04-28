package lars.core.celestial.body

import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

/**
  * Micro bodies are bodies that have a surface too small to land on.  This includes dust clouds, debris, comets, and
  * meteoroids.
  *
  * See: https://docs.google.com/document/d/1lVRbnwU_s0HDtwDi4NilufJVVlvo9wmZjp7FL1K3ewU
  *
  * @param mass
  * @param location
  * @param size
  */
class MicroBody(override var name: Option[String],
                override var mass: Mass,
                override var location: Vec2,
                override var velocity: Velocity,
                var size: Length,
                override var parent: Option[Parent with Child])
  extends StandardBody {
  override def collide(other: Sizeable): Unit = ???
}
