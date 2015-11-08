package lars.game.engine.math

import lars.game.engine.Types
import lars.game.engine.Types.DistanceType

case class Vector2(x: DistanceType, y: DistanceType) extends Ordered[Vector2] {
  def +(that: Vector2): Vector2 =
    new Vector2(x + that.x, y + that.y)

  def -(vec: Vector2): Vector2 =
    new Vector2(x - vec.x, y - vec.y)

  def *(factor: DistanceType): Vector2 =
    new Vector2(x * factor, y * factor)

  def /(factor: DistanceType): Vector2 =
    new Vector2(x / factor, y / factor)

  override def >(other: Vector2): Boolean =
    length > other.length

  override def <(other: Vector2): Boolean =
    length < other.length

  override def >=(other: Vector2): Boolean =
    length >= other.length

  override def <=(other: Vector2): Boolean =
    length <= other.length

  override def compare(that: Vector2): Int =
    (length - that.length).toInt

  def length: DistanceType =
    Vector2.distance(Vector2.addIdent, this)

  override def equals(other: Any): Boolean = other match {
    case that: Vector2 => x == that.x && y == that.y
    case _ => false
  }

  def round: Vector2 =
    new Vector2(math.round(x), math.round(y))
}

object Vector2 {
  /**
    * The additive identity.
    */
  val addIdent = new Vector2(Types.zeroDistance, Types.zeroDistance)

  /**
    * The multiplicative identity.
    */
  val mulIdent = new Vector2(Types.oneDistance, Types.oneDistance)

  /**
    * Returns the midpoint between two points.
    * @param one point1
    * @param two point2
    * @return midpoint
    */
  def midpoint(one: Vector2, two: Vector2): Vector2 =
    new Vector2((one.x + two.x)/2, (one.y + two.y)/2)

  /**
    * Returns the distance between two points.
    * @param one point1
    * @param two point2
    * @return distance
    */
  def distance(one: Vector2, two: Vector2): DistanceType =
    math.sqrt(math.pow(two.x - one.x, 2) + math.pow(two.y - one.y, 2))
}