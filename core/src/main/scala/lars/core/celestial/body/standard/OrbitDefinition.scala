package lars.core.celestial.body.standard

import lars.core.math.Circle
import lars.core.physics.units.{Length, Speed, Time}

case class OrbitDefinition(radius: Length, period: Time) {
  val speed: Speed = Circle.circumference(radius) / period
}