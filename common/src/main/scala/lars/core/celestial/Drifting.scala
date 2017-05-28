package lars.core.celestial

import lars.core.physics.celestial.gravitation.ForceCalculator
import lars.core.physics.units.{Time, Velocity}

trait Drifting {
  var velocity: Option[Velocity]

  def update(calculator: ForceCalculator, time: Time)
}