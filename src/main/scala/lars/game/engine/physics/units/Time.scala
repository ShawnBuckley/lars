package lars.game.engine.physics.units

import lars.game.engine.physics.units.Time.TimeType

class Time(val d: TimeType) {
  def us: TimeType =
    d * 86400000000L

  def ms: TimeType =
    d * 86400000

  def s: TimeType =
    d * 86400

  def m: TimeType =
    d * 1440

  def h: TimeType =
    d / 24

  def y: TimeType =
    d * 365
}

object Time {
  type TimeType = Double

  val zero: TimeType = 0
}