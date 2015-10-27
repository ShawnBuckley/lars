package lars.game.engine.celestial.body

import lars.game.engine.Location
import lars.game.engine.celestial.{Sizeable, Massive}
import lars.game.engine.math.Vector2

class TerrestrialBody(mass: Long, loc: Location, orbit: (Double) => Location, size: Long, primary: Massive, dist: Long, drift: Vector2)
  extends CelestialBody(mass, loc, size, primary, orbit, dist, drift) {

  override def collide(other: Sizeable, velocity: Vector2): Unit = {

  }

  override def observe(): Unit = ???
}
