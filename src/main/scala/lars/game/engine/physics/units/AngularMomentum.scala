package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class AngularMomentum(kgmms: LengthType) {
  def /(that: Length): Momentum =
    new Momentum(kgmms / that.m)
}
