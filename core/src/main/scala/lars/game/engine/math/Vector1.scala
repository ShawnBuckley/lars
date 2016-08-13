package lars.game.engine.math

import lars.game.engine.math.Vector1.VectorType

case class Vector1(x: VectorType) extends Ordered[Vector1] {
  def +(that: Vector1): Vector1 =
    new Vector1(x + that.x)

  def -(that: Vector1): Vector1 =
    new Vector1(x - that.x)

  def *(that: VectorType): Vector1 =
    new Vector1(x * that)

  def /(that: VectorType): Vector1 =
    new Vector1(x / that)

  override def >(other: Vector1): Boolean =
    x > other.x

  override def <(other: Vector1): Boolean =
    x < other.x

  override def >=(other: Vector1): Boolean =
    x >= other.x

  override def <=(other: Vector1): Boolean =
    x <= other.x

  override def compare(that: Vector1): Int =
    (x - that.x).toInt
}

object Vector1 {
  type VectorType = Long
}