package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Length, Mass}

/**
  * Micro bodies are bodies that have a surface too small to land on.  This includes dust clouds, debris, comets, and
  * meteoroids.
  *
  * See: https://docs.google.com/document/d/1lVRbnwU_s0HDtwDi4NilufJVVlvo9wmZjp7FL1K3ewU
  *
  * @param mass
  * @param loc
  * @param size
  */
class MicroBody(mass: Mass, loc: Vector2, size: Length, parent: Parent) extends CelestialBody(mass, loc, size, parent) {
  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???
}
