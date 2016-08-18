package lars.core.math

import lars.core.physics.units.{Length, Volume}

object Sphere {
  def volume(r: Length): Volume =
    new Volume((4/3)*math.Pi*math.pow(r.km,3))
}
