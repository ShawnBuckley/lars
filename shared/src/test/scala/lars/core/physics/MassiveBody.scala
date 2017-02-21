package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}

class MassiveBody(override var mass: Mass, override var location: Vec2) extends Massive {
  override var velocity: Velocity = _
}