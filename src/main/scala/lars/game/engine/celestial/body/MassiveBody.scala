package lars.game.engine.celestial.body

import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import lars.game.engine.physics.Mass

class MassiveBody(_mass: Mass, _location: Vector2) extends Massive {
  var loc = _location
  var vel = Vector2.addIdent

  override def mass: Mass =
    _mass

  override def location_=(_loc: Vector2): Unit =
    loc = _loc

  override def location: Vector2 =
    loc

  override def observe(): Unit =
    {}

  override def drift_=(vec: Vector2): Unit =
    vel = vec

  override def drift: Vector2 =
    vel
}
