package lars.core.physics.units

import lars.core.math.Vector1

case class Time(d: Double) extends Vector1[Time] {
  override def +(that: Time) = new Time(d + that.d)
  override def -(that: Time) = new Time(d - that.d)
  override def *(scalar: Double) = new Time(d * scalar)
  override def /(scalar: Double) = new Time(d / scalar)
  override def /(that: Time): Double = d / that.d

  override def unary_- = new Time(-d)

  override def compare(that: Time) = d.compare(that.d)

  override def midpoint(that: Time): Time = new Time(Vector1.midpoint(d, that.d))
  override def distance(that: Time): Double = Vector1.distance(d, that.d)
  override def magnitude: Double = d

  def us = d * Time.day.microseconds
  def ms = d * Time.day.milliseconds
  def s = d * Time.day.seconds
  def m = d * Time.day.minutes
  def h = d * Time.day.hours
  def w = d * Time.day.weeks
  def y = d * Time.day.years
}

object Time {
  val zero = Time(0)
  val second = Time(1/Time.day.seconds)
  val minute = second * 60

  def seconds(s: Double) = new Time(s/day.seconds)

  object day {
    val microseconds = 86400000000.0
    val milliseconds = 86400000.0
    val seconds = 86400.0
    val minutes = 1440.0
    val hours = 24.0
    val weeks = 1/7
    val years = 1/365
  }
}