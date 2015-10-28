package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Sizeable {
  def size: Long
  def density: Double
  def collide(other: Sizeable, velocity: Vector2)
}
