package lars.game.engine.celestial

import lars.game.engine.{Location, Gametime}

trait Massive {
  val mass:Long
  var location: Location
  var lastSeen: Gametime
  def observe()
}
