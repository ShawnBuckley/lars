package lars.core.celestial.body.standard

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vector2
import lars.core.physics.units.{Length, Mass, Time, Velocity}

/**
  * Gaseous bodies are any body that consists on a non solid outermost surface.  This includes gas clouds, gas dwarfs,
  * gas giants, and brown dwarfs.
  *
  * See: https://docs.google.com/document/d/1i7DdZ9atqvvfzx_K6bwlE2WKCwg3kxGPfTSWkG_VmO4
  *
  * @param mass
  * @param location
  * @param size
  */
class GaseousBody(override var name: String,
                  override var mass: Mass,
                  override var location: Vector2,
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
