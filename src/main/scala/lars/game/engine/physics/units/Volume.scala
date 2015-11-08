package lars.game.engine.physics.units

import lars.game.engine.physics.units.Volume.VolumeType


class Volume(volume: VolumeType) {
  def km3: VolumeType =
    volume
}

object Volume {
  type VolumeType = Double

  val zero: VolumeType = 0
}