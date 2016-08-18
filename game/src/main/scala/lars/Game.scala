package lars

import lars.core.Gametime
import lars.core.celestial.container.Galaxy

object Game {
  val galaxy = new Galaxy
  var time = new Gametime(0)
}
