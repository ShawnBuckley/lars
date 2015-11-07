package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Parent, Sizeable, Massive}
import lars.game.engine.math.Vector2

class TerrestrialBody(mass: Long, loc: Vector2, size: Long, primary: Massive, dist: Long, drift: Vector2)
  extends CelestialBody(mass, loc, size, primary, dist, drift) {

  override def collide(other: Sizeable, velocity: Vector2): Unit = {
    // map space location to surface location
    // create crater
  }

  override def observe(): Unit = {

  }

  override var par: Parent = _
}
