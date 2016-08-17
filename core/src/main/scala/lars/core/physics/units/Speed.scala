package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Speed(ms: LengthType) {
  def kms: LengthType =
    ms / 1000
}