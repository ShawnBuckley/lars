package lars.game.engine.physics.units

import lars.game.engine.physics.units.Length.LengthType

case class Length(km: LengthType) {
  def m: LengthType =
    km * 1000

  def au: LengthType =
    km / 149597870.7


  def -(that: Length): Length =
    new Length(km - that.km)

  def *(that: Length): Area =
    new Area(km * that.km)

  def /(that: Time): Speed =
    new Speed(m / that.s)

  def /(that: Length): LengthType =
    km / that.km
}

object Length {
  type LengthType = Double

  val zero: LengthType = 0
  val one:  LengthType = 1

  object in {
    def m(m: LengthType): Length =
      new Length(m * Km.m)
  }

  /**
    * Constants used for converting length measurements into kilometers.
    */
  object Km {
    val m = 1e-3
    val mile = 0.6
    val km = 1
    val au = 149597871
    val lightyear = 9460528400000L
    val parsec = 30856775800000L
  }
}