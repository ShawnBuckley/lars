package lars.core.surface

import lars.core.celestial.Body
import lars.core.physics.Physics
import lars.core.physics.units.{Length, Mass, Time}

class Singularity(override val body: Body) extends Surface {
  override def observed(date: Time): Unit = ???

  override var lastObserved: Time = _

  override def collide(other: Body): Unit = {
    body.mass += other.mass
    body.size = Some(Singularity.schwarzschildRadius(body.mass))
  }
}

object Singularity {
  def schwarzschildRadius(mass: Mass): Length = {
    new Length(Physics.schwarzschildFactor.km * mass.kg)
  }
}
