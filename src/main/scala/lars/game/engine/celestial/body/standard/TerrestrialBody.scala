package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Length, Mass}

/**
  * Terrestrial bodies are all variety of celestial objects large enough to have a surface that can be visited.  This
  * includes asteroids, planetoids, moons, and planets.
  *
  * See: https://docs.google.com/document/d/1f3JKtOjPvJIYzYThT43RCBR61_LUqYzk631wu4QGxac
  *
  * @param mass
  * @param location
  * @param size
  */
class TerrestrialBody(mass: Mass, location: Vector2, size: Length, parent: Parent) extends CelestialBody(mass, location, size, parent) {

  override def observe(): Unit = {

  }

  override def collide(other: Sizeable): Unit = {
    // map space location to surface location
    // create crater
  }
}
