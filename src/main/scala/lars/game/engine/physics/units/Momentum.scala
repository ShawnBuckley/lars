package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Momentum(kgms: LengthType) {
  def /(that: Mass): Speed =
    new Speed(kgms / that.kg)

  def *(that: Length): AngularMomentum =
    new AngularMomentum(kgms * that.m)
}