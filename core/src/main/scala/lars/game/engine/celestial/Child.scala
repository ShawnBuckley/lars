package lars.game.engine.celestial

import lars.game.engine.math.Vector2

/**
  * This trait is used to determine a nested object's absolute location in the galaxy.
  */
trait Child extends NestedLocation {
  var parent: Parent

  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    *
    * @param relative relative location
    * @return absolute location
    */
  override def absoluteLocation(relative: Vector2): Vector2 =
    parent.absoluteLocation(relative)
}
