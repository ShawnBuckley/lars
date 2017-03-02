package lars.core.physics.units

import lars.core.math.Vector1

case class Length(km: Double) extends Vector1[Length] {
  override def +(that: Length) = new Length(km + that.km)
  override def -(that: Length) = new Length(km - that.km)
  override def *(scalar: Double) = new Length(km * scalar)
  override def /(scalar: Double) = new Length(km / scalar)
  override def /(that: Length) = km / that.km

  def unary_- = new Length(-km)

  override def compare(that: Length) = km.compare(that.km)

  override def midpoint(that: Length): Length = new Length(Vector1.midpoint(km, that.km))
  override def distance(that: Length): Double = Vector1.distance(km, that.km)
  override def magnitude: Double = km

  // Conversions to that lengths

  def m: Double = km / Length.Km.m
  def au: Double = km / Length.Km.au

  // Conversions to that units

  def *(that: Length)= new Area(km * that.km)

  def /(that: Time) = new Speed(m / that.s)
}

object Length {
  val zero = new Length(0)
  val one = new Length(1)

  object in {
    def m(m: Double) = new Length(m * Km.m)
  }

  /**
    * Constants for converting length measurements into kilometers.
    */
  object Km {
    val m = 1e-3
    val mile = 0.6
    val km = 1.0
    val au = 149597870.7
    val lightyear = 9460528400000.0
    val parsec = 30856775800000.0
  }
}