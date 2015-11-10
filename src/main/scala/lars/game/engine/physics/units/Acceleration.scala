package lars.game.engine.physics.units

import lars.game.engine.physics.units.Acceleration.AccelType

case class Acceleration(ms2: AccelType) {

}

object Acceleration {
  type AccelType = Double
}