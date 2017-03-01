package lars

import lars.core.Gametime
import lars.core.celestial.container.Galaxy

object Game {
  var galaxy: Galaxy = _
  var time = new Gametime(0)
}
