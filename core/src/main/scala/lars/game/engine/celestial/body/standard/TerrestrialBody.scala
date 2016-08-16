package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.{Child, Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Velocity, Length, Mass}

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
class TerrestrialBody(override var mass: Mass,
                      override var location: Vector2,
                      override var velocity: Velocity,
                      override var size: Length,
                      override var parent: Parent) extends Sizeable with Child {

  override def observe(): Unit = {

  }

  override def collide(other: Sizeable): Unit = {
    // map space location to surface location
    // create crater
  }
}