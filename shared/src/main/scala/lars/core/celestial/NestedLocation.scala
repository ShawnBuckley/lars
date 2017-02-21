package lars.core.celestial

import lars.core.math.Vec2

/**
  * NestedLocation allows for transforming relative coordinates to absolute coordinates.  See the Parent and Child
  * traits.
  */
trait NestedLocation {
  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    * @param relative relative location
    * @return absolute location
    */
  def absoluteLocation(relative: Vec2): Vec2
}
