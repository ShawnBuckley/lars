package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2

class GaseousBody(mass: Long, loc: Vector2, size: Long) extends CelestialBody(mass, loc, size) {
  override var par: Parent = _

  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???
}
