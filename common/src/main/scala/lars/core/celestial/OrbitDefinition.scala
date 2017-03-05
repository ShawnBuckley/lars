package lars.core.celestial

import lars.core.math.Circle
import lars.core.physics.units.{Length, Speed, Time}

case class OrbitDefinition(radius: Length, period: Time) {
  val speed: Speed = {
    if(period.d == 0)
      Speed.zero
    else
      Circle.circumference(radius) / period
  }
}

object OrbitDefinition {
  val none = OrbitDefinition(Length.zero, Time.zero)
}