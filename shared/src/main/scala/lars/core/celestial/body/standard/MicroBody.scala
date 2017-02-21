package lars.core.celestial.body.standard

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Time, Velocity}

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
class MicroBody(override var name: String,
                override var mass: Mass,
                override var location: Vec2,
                override var velocity: Velocity,
                override var size: Length,
                override var parent: Parent)
  extends Sizeable
    with Child
    with Observable
    with Nameable {
  override def observe(time: Time): Unit = {}

  override def collide(other: Sizeable): Unit = ???
}
