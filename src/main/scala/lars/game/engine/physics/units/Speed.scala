package lars.game.engine.physics.units

import lars.game.engine.physics.units.Speed.SpeedType

case class Speed(ms: SpeedType) {
  def kms: SpeedType =
    ms / 1000
}

object Speed {
  type SpeedType = Double

  val zero: SpeedType = 0
}