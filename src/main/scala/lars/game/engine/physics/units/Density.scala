package lars.game.engine.physics.units

import lars.game.engine.physics.units.Density.DensityType


case class Density(kgm3: DensityType) {

}

object Density {
  type DensityType = Double

  val zero: DensityType = 0

  def calculate(mass: Mass, length: Length): Density =
    this(mass.kg / length.km)


}
