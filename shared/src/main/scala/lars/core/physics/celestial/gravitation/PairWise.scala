package lars.core.physics.celestial.gravitation

import lars.core.celestial.Massive
import lars.core.physics.units.Force
import lars.core.physics.Barycenter

/**
  * Simple pair-wise gravity force calculation.
 */
class PairWise(massives: Seq[Massive]) extends ForceCalculator {
  val barycenter: Barycenter = Barycenter.calculate(massives)

  override def calculate(body: Massive): Force = {
    var force = Force.zero
    massives.filter(_ != body).foreach(massive => {
      force += massive.gravForce(body)
    })
    force
  }
}
