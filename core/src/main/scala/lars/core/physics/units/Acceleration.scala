package lars.core.physics.units

import lars.core.math.Vector2

case class Acceleration(ms2: Vector2) {
  def /(that: Time): Velocity =
    new Velocity(ms2 * that.s)
}