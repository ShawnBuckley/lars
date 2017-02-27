package lars

import lars.core.Gametime
import lars.core.celestial.container.Galaxy

object Game {
  val galaxy = new Galaxy(Some("Milky Way"))
  var time = new Gametime(0)
}
