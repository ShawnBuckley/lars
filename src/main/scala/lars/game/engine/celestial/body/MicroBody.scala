package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Sizeable, Parent}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.{Length, Mass}

class MicroBody(mass: Mass, loc: Vector2, size: Length) extends CelestialBody(mass, loc, size) {
  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???

  override var par: Parent = _
}
