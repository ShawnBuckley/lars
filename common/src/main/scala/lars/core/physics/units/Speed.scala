package lars.core.physics.units

import lars.core.math.Vector1

case class Speed(ms: Double) extends Vector1[Speed] {
  override def +(that: Speed) = new Speed(ms + that.ms)
  override def -(that: Speed) = new Speed(ms - that.ms)
  override def *(scalar: Double) = new Speed(ms * scalar)
  override def /(scalar: Double) = new Speed(ms / scalar)
  override def /(that: Speed): Double = ms / that.ms

  override def unary_- : Speed = new Speed(-ms)

  override def compare(that: Speed): Int = ms.compare(ms)

  override def midpoint(that: Speed): Speed = new Speed(Vector1.midpoint(ms, that.ms))
  override def distance(that: Speed) = Vector1.distance(ms, that.ms)
  override def magnitude: Double = ms

  // Conversions to other types

  def kms = ms / Length.Km.m
  def kmh = ms * 3.6

  def *(that: Time) = new Length(kms * that.s)
}

object Speed {
  val zero = new Speed(0)
}