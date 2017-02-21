package lars.core.math

trait Vector1[T] extends Ordered[T] {
  def +(that: T): T
  def -(that: T): T
  def *(scalar: Double): T
  def /(scalar: Double): T
  def /(that: T): Double

  def unary_- : T

  def midpoint(that: T): T
  def distance(that: T): Double
  def magnitude: Double
}

object Vector1 {
  def midpoint(a: Double, b: Double) = (a + b) / 2
  def distance(a: Double, b: Double) = math.abs(a - b)
}