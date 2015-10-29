package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Parent {
  def absoluteLocation(loc: Vector2): Vector2
}
