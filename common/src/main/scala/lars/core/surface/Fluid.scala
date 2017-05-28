package lars.core.surface

import lars.core.celestial.Body
import lars.core.physics.units.Time

class Fluid(override val body: Body) extends Surface {
  override def observed(date: Time): Unit = ???

  override var lastObserved: Time = _

  override def collide(other: Body): Unit = ???
}
