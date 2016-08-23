package lars.core.physics.units

import lars.core.math.{Vec2, Vector2}

case class AngularMomentum(kgmms: Vec2) extends Vector2[AngularMomentum] {
  def +(that: AngularMomentum) = new AngularMomentum(kgmms + that.kgmms)
  def -(that: AngularMomentum) = new AngularMomentum(kgmms + that.kgmms)
  def *(scalar: Double) = new AngularMomentum(kgmms * scalar)
  def /(scalar: Double) = new AngularMomentum(kgmms / scalar)
  def /(that: AngularMomentum) = kgmms / that.kgmms

  def unary_- = new AngularMomentum(-kgmms)

  override def compare(that: AngularMomentum): Int = kgmms.compare(that.kgmms)

  def midpoint(that: AngularMomentum) = new AngularMomentum(kgmms.midpoint(that.kgmms))
  def distance(that: AngularMomentum) = new AngularMomentum(kgmms.distance(that.kgmms))
  def magnitude = kgmms.magnitude
  def normalize = new AngularMomentum(kgmms.normalize)
  def angle = kgmms.angle


  def /(that: Length): Momentum =
    new Momentum(kgmms / that.m)
}

// TODO - calculate with sin of angle
// http://hyperphysics.phy-astr.gsu.edu/hbase/amom.html
object AngularMomentum {
  def conserve(velocity: Velocity, radius: Length, newRadius: Length): Velocity =
    velocity * (radius / newRadius)
}