package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Mass, Length}

class GaseousBody(mass: Mass, loc: Vector2, size: Length) extends CelestialBody(mass, loc, size) {
  override var par: Parent = _

  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???
}
