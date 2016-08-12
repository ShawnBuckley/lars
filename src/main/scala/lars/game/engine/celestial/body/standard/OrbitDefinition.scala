package lars.game.engine.celestial.body.standard

import lars.game.engine.math.Circle
import lars.game.engine.physics.units.{Length, Speed, Time}

case class OrbitDefinition(val radius: Length, val period: Time) {
  val speed: Speed = Circle.circumference(radius) / period
}