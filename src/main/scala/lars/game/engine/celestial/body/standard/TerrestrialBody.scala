package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.{Length, Mass}

/**
  * Terrestrial bodies are all variety of celestial objects large enough to have a surface that can be visited.  This
  * includes asteroids, planetoids, moons, and planets.
  *
  * See: https://docs.google.com/document/d/1f3JKtOjPvJIYzYThT43RCBR61_LUqYzk631wu4QGxac
  *
  * @param mass
  * @param loc
  * @param size
  */
class TerrestrialBody(mass: Mass, loc: Vector2, size: Length)
  extends CelestialBody(mass, loc, size) {

  override def collide(other: Sizeable, velocity: Vector2): Unit = {
    // map space location to surface location
    // create crater
  }

  override def observe(): Unit = {

  }

  override var par: Parent = _
}
