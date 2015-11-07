package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Massive extends Ordered[Massive] {
  def mass: Long
  def location: Vector2
  def location_=(loc: Vector2)
  def drift: Vector2
  def drift_=(vec: Vector2)
  def observe()

  override def >(other: Massive): Boolean = mass > other.mass
  override def <(other: Massive): Boolean = mass < other.mass
  override def >=(other: Massive): Boolean = mass >= other.mass
  override def <=(other: Massive): Boolean = mass <= other.mass

  def +(that: Massive): Long = mass + that.mass
  def +(that: Long): Long = mass + that

  override def compare(that: Massive): Int = (mass - that.mass).toInt
}

object Massive {
  def min(m1: Massive, m2: Massive): Massive = if(m1 < m2) m1 else m2
  def max(m1: Massive, m2: Massive): Massive = if(m1 > m2) m1 else m2

  /**
    * Returns a tuple with the larger Massive first.
    * @param m1
    * @param m2
    * @return tuple, larger Massive first
    */
  def sort(m1: Massive, m2: Massive): (Massive, Massive) = {
    if(m1 > m2) (m1, m2) else (m2,m1)
  }
}
