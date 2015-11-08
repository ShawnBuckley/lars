package lars.game.engine.physics.units

import lars.game.engine.physics.units.Temperature.TemperatureType

class Temperature(val k: TemperatureType) {

}

object Temperature {
  type TemperatureType = Double

  val zero: TemperatureType = 0
}