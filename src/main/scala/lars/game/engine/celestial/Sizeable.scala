package lars.game.engine.celestial

import lars.game.engine.math.{Sphere, Vector2}
import lars.game.engine.physics.units.{Mass, Density, Length}

class Sizeable(_mass: Mass, _loc: Vector2, size: Length) extends Massive {
  var loc = _loc
  var vec = Vector2.addIdent

  override def mass: Mass =
    _mass

  override def location: Vector2 =
    loc

  override def location_=(loc: Vector2): Unit =
    this.loc = loc

  override def observe(): Unit =
    {}

  override def drift: Vector2 =
    vec

  override def drift_=(vec: Vector2): Unit =
    this.vec = vec

  def radius: Length =
    size

  def density: Density =
    new Density(mass.kg / Sphere.volume(radius).km3)

  def collide(other: Sizeable, velocity: Vector2) =
    {}
}
