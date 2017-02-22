package lars.core.physics.units

import lars.core.math.Vec2

case class Momentum(mass: Mass, velocity: Velocity) {
  def kgms: Vec2 = velocity.ms * mass.kg
}