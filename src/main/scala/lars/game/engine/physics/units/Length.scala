package lars.game.engine.physics.units

import lars.game.engine.Types.DistanceType

case class Length(length: DistanceType) {
  /**
    * Returns length in the standard unit of measure, km.
    * @return km
    */
  def km: DistanceType =
    length
}
