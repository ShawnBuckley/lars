package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Area(km2: LengthType) {
  def m2: LengthType =
    km2 * 1000

  def *(that: Length): Volume =
    new Volume(km2 * that.km)

  def *(that: Force): Pressure =
    new Pressure(m2 * that.N.length)
}