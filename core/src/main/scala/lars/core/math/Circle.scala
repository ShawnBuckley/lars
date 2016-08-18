package lars.core.math

import lars.core.physics.units.Length

object Circle {
  def circumference(r: Length): Length =
    new Length(2 * math.Pi * r.km)
}
