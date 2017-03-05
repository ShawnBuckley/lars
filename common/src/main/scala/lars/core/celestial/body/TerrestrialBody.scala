package lars.core.celestial.body

import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.observation.Observable
import lars.core.physics.units.{Length, Mass, Time, Velocity}

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
class TerrestrialBody(override var name: Option[String],
                      override var mass: Mass,
                      override var location: Vec2,
                      override var velocity: Velocity,
                      override var size: Length,
                      override var parent: Option[Parent with Child])
  extends StandardBody with Observable {

  /**
    * Observe an object.  The time passed is the current simulation date.
    *
    * @param date the current simulation time
    */
  override def observed(date: Time): Unit = {

  }

  override def collide(other: Sizeable): Unit = {
    // map space location to surface location
    // create crater
  }
}
