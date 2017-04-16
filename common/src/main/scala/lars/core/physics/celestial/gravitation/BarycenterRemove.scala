package lars.core.physics.celestial.gravitation
import lars.core.celestial.Massive
import lars.core.physics.Barycenter
import lars.core.physics.units.Force

class BarycenterRemove(massives: Seq[Massive]) extends ForceCalculator {
  val barycenter: Barycenter = Barycenter.calculate(massives)

  override def calculate(massive: Massive): Force =
    barycenter.remove(massive).gravForce(massive)
}
