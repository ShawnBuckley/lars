package lars.game.engine.physics.units

import lars.game.engine.physics.units.Acceleration.AccelType

class Acceleration(val ms2: AccelType) {

}
object Acceleration {
  type AccelType = Double
}