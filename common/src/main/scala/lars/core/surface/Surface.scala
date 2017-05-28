package lars.core.surface

import lars.core.celestial.Body
import lars.core.observation.Observable

trait Surface extends Observable {
  val body: Body

  def collide(other: Body)
}
