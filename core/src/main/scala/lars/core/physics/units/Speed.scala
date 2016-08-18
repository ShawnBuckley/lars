package lars.core.physics.units

import lars.core.physics.units.Length.LengthType

case class Speed(ms: LengthType) {
  def kms: LengthType =
    ms / 1000
}