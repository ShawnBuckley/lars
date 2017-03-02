package lars.core.celestial.definition

import lars.core.math.Circle
import lars.core.physics.units.{Length, Speed, Time}

case class Orbit(length: Double, period: Double) {
  def speed: Speed = {
    if(period == 0)
      Speed.zero
    else
      Circle.circumference(Length(length)) / Time(period)
  }
}