package lars.core.physics.units

import lars.core.physics.units.Temperature.TemperatureType

case class Temperature(k: TemperatureType) {

}

object Temperature {
  type TemperatureType = Double

  val zero: TemperatureType = 0
}