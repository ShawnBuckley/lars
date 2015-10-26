package lars.game.engine.celestial

import lars.game.engine.Location

trait Orbiting {
  val primary: Massive
  val distance: Long
  def getLocation(period: Double): Location
}
