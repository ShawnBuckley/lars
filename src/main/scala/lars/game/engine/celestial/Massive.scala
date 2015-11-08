package lars.game.engine.celestial

import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

class Massive(var mass: Mass, var location: Vector2, var drift: Vector2 = Vector2.addIdent) {
  def observe(): Unit =
    {}
}
