package lars.game.engine.celestial

import lars.game.engine.math.Vector2

trait Child {
  var par: Parent

  /**
   * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
   * it's coordinate system.
   * @param relative relative location
   * @return absolute location
   */
  def absoluteLocation(relative: Vector2): Vector2

  /**
   * Returns the parent
   * @return parent
   */
  def parent: Parent =
    par

  /**
   * Sets a new parent.
   * @param par new parent
   */
  def parent_=(par: Parent) = this.par = par
}
