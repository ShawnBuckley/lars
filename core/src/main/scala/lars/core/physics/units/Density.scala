package lars.core.physics.units

import lars.core.physics.units.Density.DensityType


case class Density(kgm3: DensityType) {

}

object Density {
  type DensityType = Double

  val zero: DensityType = 0

  def calculate(mass: Mass, volume: Volume): Density =
    this(mass.kg / volume.km3)
}
