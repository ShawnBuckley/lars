package lars.game.engine.math

import lars.game.engine.physics.units.{Volume, Length}

object Sphere {
  def volume(r: Length): Volume =
    new Volume(1.333333333*math.Pi*math.pow(r.km,3))
}
