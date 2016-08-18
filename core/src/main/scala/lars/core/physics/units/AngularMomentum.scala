package lars.core.physics.units

import lars.core.math.Vector2

case class AngularMomentum(kgmms: Vector2) {
  def /(that: Length): Momentum =
    new Momentum(kgmms / that.m)
}

// TODO - calculate with sin of angle
object AngularMomentum {
  def conserve(velocity: Velocity, radius: Length, newRadius: Length): Velocity =
    velocity * (radius / newRadius)
}