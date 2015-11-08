package lars.game.engine.physics.units

import lars.game.engine.physics.units.Speed.SpeedType

case class Speed(val KmS: SpeedType) {

}

object Speed {
  type SpeedType = Double

  val zero: SpeedType = 0
}