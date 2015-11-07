package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Sizeable, Parent}
import lars.game.engine.math.Vector2

class MicroBody(mass: Long, loc: Vector2, size: Long) extends CelestialBody(mass, loc, size) {
  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???

  override var par: Parent = _
}
