package lars.game.engine

import lars.game.engine.math.Vector2

case class Location(location: Vector2) {
  def get(): Vector2 = location
}
