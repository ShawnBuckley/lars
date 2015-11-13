package lars.game.engine.physics

import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

/**
  * This is a computed barycenter.  It extends Massive and can be used as such.
  * @param mass
  * @param location
  */
case class Barycenter(override var mass: Mass, override var location: Vector2) extends Massive {
  override def observe(): Unit = {}
}
