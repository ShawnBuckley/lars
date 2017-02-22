package lars.core.math

import lars.core.physics.units.Length

object Circle {
  def circumference(radius: Length): Length =
    new Length(2 * math.Pi * radius.km)

  def centralAngle(radius: Length, length: Length): Double =
    length.km / radius.km
}
