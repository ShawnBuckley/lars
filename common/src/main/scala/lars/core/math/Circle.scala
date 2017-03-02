package lars.core.math

import lars.core.physics.units.Length

object Circle {
  def circumference(radius: Length): Length =
    new Length(2 * math.Pi * radius.km)

  def centralAngle(radius: Length, length: Length): Double = {
    if(radius.km == 0)
      0
    else
      length.km / radius.km
  }
}
