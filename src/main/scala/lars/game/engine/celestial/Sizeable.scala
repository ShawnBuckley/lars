package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Sizeable {
  val size: Long
  def collide(other: Sizeable, velocity: Vector2)
}
