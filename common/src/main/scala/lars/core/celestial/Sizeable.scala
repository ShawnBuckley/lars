package lars.core.celestial

import lars.core.physics.units.Length

trait Sizeable {
  def size: Option[Length]
  def size_=(length: Option[Length]): Unit
}
