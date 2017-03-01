package lars.core.physics.units

import lars.core.math.{Vec2, Vector2}

case class Force(N: Vec2) extends Vector2[Force] {
  def +(that: Force) = new Force(N + that.N)
  def -(that: Force) = new Force(N - that.N)
  def *(scalar: Double) = new Force(N * scalar)
  def /(scalar: Double) = new Force(N / scalar)
  def /(that: Force): Double = N / that.N

  def unary_- = new Force(-N)

  override def compare(that: Force): Int = N.compare(that.N)

  override def inverse: Force = Force(N.inverse)
  def midpoint(that: Force) = new Force(N.midpoint(that.N))
  def distance(that: Force) = new Force(N.distance(that.N))
  def magnitude: Double = N.magnitude
  def normalize = new Force(N.normalize)
  def angle: Double = N.angle
  
  
  def *(that: Area): Pressure =
    new Pressure(N.magnitude * that.m2)

  def /(that: Mass): Acceleration =
    new Acceleration(N / that.kg)
}

object Force {
  val zero = Force(Vec2.addIdent)
}