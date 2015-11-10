package lars.game.engine.physics.units

import lars.game.engine.physics.units.Volume.VolumeType


case class Volume(km3: VolumeType) {

}

object Volume {
  type VolumeType = Double

  val zero: VolumeType = 0
}