package lars.game.engine.physics

case class Density(density: Double) {

  def this(mass: Mass, length: Length) {
    this(mass.kg / length.km)
  }

  /**
    * Returns density as the standard unit of measure, kg/m3.
    * @return kg/m3
    */
  def kgm3: Double = density
}
