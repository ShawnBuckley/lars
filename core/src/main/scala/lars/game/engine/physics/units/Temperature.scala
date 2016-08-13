package lars.game.engine.physics.units

import lars.game.engine.physics.units.Temperature.TemperatureType

case class Temperature(k: TemperatureType) {

}

object Temperature {
  type TemperatureType = Double

  val zero: TemperatureType = 0
}