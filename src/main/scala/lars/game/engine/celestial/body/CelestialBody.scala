package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Orbiting, Sizeable, Massive}
import lars.game.engine.math.{Sphere, Vector2}

abstract class CelestialBody(
                              _mass: Long,
                              _loc: Vector2,
                              _size: Long,
                              _primary: Massive,
                              _orbit:(Double) => Vector2,
                              _dist: Long, dri: Vector2)
  extends Massive with Sizeable with Orbiting {

  var loc = _loc
  var vel = Vector2(0,0)

  override def mass: Long = _mass

  override def location: Vector2 = loc

  override def location_=(loc: Vector2) = this.loc = loc

  override def size: Long = _size

  override def distance: Long = _dist

  override def primary: Massive = _primary

  override def getLocation(period: Double): Vector2 = _orbit(period)

  override def drift_=(vec: Vector2): Unit = vel = vec

  override def drift: Vector2 = vel

  override def density: Double = mass/Sphere.volume(size)
}
