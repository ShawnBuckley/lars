package lars.core.math

trait Vector2[T] extends Ordered[T] {
  def +(that: T): T
  def -(that: T): T
  def *(scalar: Double): T
  def /(scalar: Double): T
  def /(that: T): Double

  def unary_- : T

  def midpoint(that: T): T
  def distance(that: T): T
  def magnitude: Double
  def normalize: T
  def angle: Double
}

case class Vec2(x: Double, y: Double) extends Ordered[Vec2] {
  def this(a: Double) = this(a, a)

  def +(that: Vec2) = new Vec2(x + that.x, y + that.y)
  def -(that: Vec2) = new Vec2(x - that.x, y - that.y)
  def *(scalar: Double) = new Vec2(x * scalar, y * scalar)
  def /(scalar: Double) = new Vec2(x / scalar, y / scalar)
  def /(that: Vec2) = magnitude / that.magnitude

  def unary_- = new Vec2(-x, -y)

  override def compare(that: Vec2) = magnitude.compare(that.magnitude)

  def inverse: Vec2 = Vec2(y, x)
  def midpoint(that: Vec2) = new Vec2((x + that.x)/2, (y + that.y)/2)
  def distance(that: Vec2) = new Vec2(x - that.x, y - that.y)
  def magnitude = math.sqrt(math.pow(x, 2) + math.pow(y, 2))

  override def equals(that: Any): Boolean = that match {
    case that: Vec2 => x == that.x && y == that.y
    case _ => false
  }

  def normalize = {
    val factor = math.abs(magnitude)
    new Vec2(x / factor, y / factor)
  }

  def angle: Double = math.atan2(y, x)

  def toPolar = new Polar2(angle, magnitude)
}

object Vec2 {
  val addIdent = new Vec2(0.0, 0.0)
  val mulIdent = 1.0
}