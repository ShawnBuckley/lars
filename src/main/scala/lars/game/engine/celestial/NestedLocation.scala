package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait NestedLocation {
  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    * @param relative relative location
    * @return absolute location
    */
  def absoluteLocation(relative: Vector2): Vector2
}