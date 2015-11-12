package lars.game.engine.physics.units

import lars.game.engine.math.Vector2

case class Acceleration(ms2: Vector2) {
  def /(that: Time): Velocity =
    new Velocity(ms2 * that.s)
}