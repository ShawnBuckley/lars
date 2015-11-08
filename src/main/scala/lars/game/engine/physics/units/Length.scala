package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Length(val km: LengthType) {

}

object Length {
  type LengthType = Double

  val zero: LengthType = 0
  val one: LengthType = 1

  object Km {
    val mile = 0.6
    val km = 1
    val au = 149597871
    val lightyear = 9460528400000L
    val parsec = 30856775800000L
  }
}