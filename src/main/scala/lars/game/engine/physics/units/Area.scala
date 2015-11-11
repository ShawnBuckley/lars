package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Area(km2: LengthType) {

  def *(that: Length): Volume =
    new Volume(km2 * that.km)
}