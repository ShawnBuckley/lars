package lars.core.physics.units

import lars.core.math.Vector2

case class Momentum(kgms: Vector2) {
  def /(that: Mass): Velocity =
    new Velocity(kgms / that.kg)

  def *(that: Length): AngularMomentum =
    new AngularMomentum(kgms * that.m)
}