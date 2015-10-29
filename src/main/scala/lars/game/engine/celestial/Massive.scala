package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Massive {
  def mass: Long
  def location: Vector2
  def location_=(loc: Vector2)
  def drift: Vector2
  def drift_=(vec: Vector2)
  def observe()
}
