package lars.game.engine.math

import lars.game.engine.physics.units.Length

object Circle {
  def circumference(r: Length): Length =
    new Length(2 * math.Pi * r.km)
}
