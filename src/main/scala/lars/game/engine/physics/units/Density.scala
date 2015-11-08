package lars.game.engine.physics.units

import lars.game.engine.physics.units.Density.DensityType


case class Density(density: DensityType) {
  /**
    * Returns density as the standard unit of measure, kg/m3.
    * @return kg/m3
    */
  def kgm3: DensityType =
    density
}

object Density {
  type DensityType = Double

  val zero: DensityType = 0

  def calculate(mass: Mass, length: Length): Density =
    this(mass.kg / length.km)


}
