package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Mass, Length}

/**
  * Gaseous bodies are any body that consists on a non solid outermost surface.  This includes gas clouds, gas dwarfs,
  * gas giants, and brown dwarfs.
  *
  * See: https://docs.google.com/document/d/1i7DdZ9atqvvfzx_K6bwlE2WKCwg3kxGPfTSWkG_VmO4
  *
  * @param mass
  * @param loc
  * @param size
  */
class GaseousBody(mass: Mass, loc: Vector2, size: Length) extends CelestialBody(mass, loc, size) {
  override var par: Parent = _

  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???
}
