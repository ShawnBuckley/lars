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

object Length {
  object Km {
    val mile = 0.6
    val km = 1
    val au = 149597871
    val lightyear = 9460528400000L
    val parsec = 30856775800000L
  }
}