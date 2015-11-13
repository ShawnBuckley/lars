package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.{Child, Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Velocity, Length, Mass}

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
class MicroBody(override var mass: Mass,
                override var location: Vector2,
                override var size: Length,
                override var parent: Parent) extends Sizeable with Child {
  override def observe(): Unit = ???

  override def collide(other: Sizeable): Unit = ???

  override var velocity: Velocity = Velocity.zero
}
