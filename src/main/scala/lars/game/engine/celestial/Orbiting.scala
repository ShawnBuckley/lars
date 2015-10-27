package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Orbiting {
  def primary: Massive
  def distance: Long
  def getLocation(period: Double): Vector2
}
