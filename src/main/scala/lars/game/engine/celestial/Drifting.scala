package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Drifting {
  def getDrift: Vector2
  def drift(): Unit
}
