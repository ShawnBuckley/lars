package lars.game.engine.math

import lars.game.engine.physics.units.{Length, Volume}

object Sphere {
  def volume(r: Length): Volume =
    new Volume(1.333333333*math.Pi*math.pow(r.km,3))
}
