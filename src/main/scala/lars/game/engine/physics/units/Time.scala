package lars.game.engine.physics.units

import lars.game.engine.physics.units.Time.TimeType

case class Time(d: TimeType) {
  def us: TimeType =
    d * Time.d.us

  def ms: TimeType =
    d * Time.d.ms

  def s: TimeType =
    d * Time.d.s

  def m: TimeType =
    d * Time.d.m

  def h: TimeType =
    d * Time.d.h

  def w: TimeType =
    d * Time.d.w

  def y: TimeType =
    d * Time.d.y

  def +(that: Time): Time =
    new Time(d + that.d)
}

object Time {
  type TimeType = Double
  val zero: TimeType = 0

  object in {
    def s(sec: TimeType): Time =
      new Time(sec / d.s)

    def m(min: TimeType): Time =
      new Time(min / d.m)
  }

  object d {
    val us = 86400000000L
    val ms = 86400000
    val s = 86400
    val m = 1440
    val h = 24
    val w = 1/7
    val y = 1/365
  }


}