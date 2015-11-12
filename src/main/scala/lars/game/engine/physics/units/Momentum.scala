package lars.game.engine.physics.units

import lars.game.engine.math.Vector2

case class Momentum(kgms: Vector2) {
  def /(that: Mass): Velocity =
    new Velocity(kgms / that.kg)

  def *(that: Length): AngularMomentum =
    new AngularMomentum(kgms * that.m)
}