package lars.game.engine.physics.units

import lars.game.engine.Types.DensityType

case class Density(density: DensityType) {
  /**
    * Returns density as the standard unit of measure, kg/m3.
    * @return kg/m3
    */
  def kgm3: DensityType =
    density
}

object Density {
  def calculate(mass: Mass, length: Length): Density =
    this(mass.kg / length.km)


}
