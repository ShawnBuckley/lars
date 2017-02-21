package lars.core.physics

import lars.core.celestial.Massive
import lars.core.physics.units.Length

class BarnesHutTree(massives: Seq[Massive], size: Length) {
  val root = new BarnesHutNode(massives.head, size)
  root.add(massives.tail)
}
