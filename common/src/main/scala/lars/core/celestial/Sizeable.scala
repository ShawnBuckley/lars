package lars.core.celestial

import lars.core.physics.units.Length

trait Sizeable {
  def size: Option[Length]
}
