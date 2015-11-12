package lars.game.engine.physics.units

import lars.game.engine.math.Vector2

case class Force(N: Vector2) {
  def *(that: Area): Pressure =
    new Pressure(N.length * that.m2)

  def /(that: Mass): Acceleration =
    new Acceleration(N / that.kg)
}