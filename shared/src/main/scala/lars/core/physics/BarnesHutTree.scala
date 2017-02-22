package lars.core.physics

import lars.core.celestial.Massive
import lars.core.physics.units.{Force, Length}

class BarnesHutTree(massives: Seq[Massive], size: Length) extends ForceCalculator {
  private val root = new BarnesHutNode(massives.head, size)
  root.add(massives.tail)

  override def barycenter: Barycenter = root

  override def calculate(massive: Massive): Force =
    root.calculate(massive)
}
