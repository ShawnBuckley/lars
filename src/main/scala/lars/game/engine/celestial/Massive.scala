package lars.game.engine.celestial

import lars.game.engine.Location

trait Massive {
  def mass(): Long
  def location(): Location
  def observe()
}
