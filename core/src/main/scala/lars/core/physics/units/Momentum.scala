package lars.core.physics.units

import lars.core.math.{Vec2, Vector2}

case class Momentum(kgms: Vec2) extends Vector2[Momentum] {
  def +(that: Momentum) = new Momentum(kgms + that.kgms)
  def -(that: Momentum) = new Momentum(kgms + that.kgms)
  def *(scalar: Double) = new Momentum(kgms * scalar)
  def /(scalar: Double) = new Momentum(kgms / scalar)
  def /(that: Momentum) = kgms / that.kgms

  def unary_- = new Momentum(-kgms)

  override def compare(that: Momentum): Int = kgms.compare(that.kgms)

  def midpoint(that: Momentum) = new Momentum(kgms.midpoint(that.kgms))
  def distance(that: Momentum) = new Momentum(kgms.distance(that.kgms))
  def magnitude = kgms.magnitude
  def normalize = new Momentum(kgms.normalize)
  def angle = kgms.angle
  
  def /(that: Mass): Velocity =
    new Velocity(kgms / that.kg)

  def *(that: Length): AngularMomentum =
    new AngularMomentum(kgms * that.m)
}