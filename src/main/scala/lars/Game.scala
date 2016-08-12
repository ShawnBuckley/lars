package lars

import lars.game.engine.Gametime
import lars.game.engine.celestial.container.Galaxy

object Game {
  val galaxy = new Galaxy
  var time = new Gametime(0)
}
