package lars.game.engine.celestial.body

import lars.game.engine.Location
import lars.game.engine.celestial.{Drifting, Orbiting, Sizeable, Massive}
import lars.game.engine.math.Vector2

abstract class CelestialBody(
                              _mass: Long,
                              _loc: Location,
                              _size: Long,
                              _primary: Massive,
                              _orbit:(Double) => Location,
                              _dist: Long, dri: Vector2)
  extends Massive with Sizeable with Orbiting with Drifting {
  var loc = _loc
  override def mass(): Long = _mass

  override def location(): Location = loc

  override def location_(loc: Location) = this.loc = loc

  override def size(): Long = _size

  override def distance(): Long = _dist

  override def primary(): Massive = _primary

  override def getLocation(period: Double): Location = _orbit(period)

  override def getDrift(): Vector2 = dri

  override def drift(): Unit = location_(new Location(location.get + getDrift))


}
