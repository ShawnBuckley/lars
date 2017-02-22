package lars.core.physics.celestial.gravitation.barneshut

import lars.core.celestial.Massive
import lars.core.physics.units.{Force, Length}
import lars.core.physics.Barycenter
import lars.core.physics.celestial.gravitation.ForceCalculator

class BarnesHutTree(massives: Seq[Massive], size: Length) extends ForceCalculator {
  private val root = new BarnesHutNode(massives.head, size)
  root.add(massives.tail)

  override def barycenter: Barycenter = root

  override def calculate(massive: Massive): Force =
    root.calculate(massive)
}
