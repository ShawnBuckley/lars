package lars.game.engine.celestial

import lars.game.engine.Location

trait Orbiting {
  def primary(): Massive
  def distance(): Long
  def getLocation(period: Double): Location
}
