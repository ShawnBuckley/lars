package lars.core.celestial

import lars.core.physics.units.Mass

/**
  * This is a trait that celestial containers will inherit.  It allows for transforming relative interior coordinates to
  * the coordinate system that the container resides in and for updating celestial parent mass on child changes.  This
  * call propagates to the eldest container to get the absolute coordinates.
  */
trait Parent extends NestedLocation {
  /**
    *
    * @param mass mass added
    */
  def add(mass: Mass)

  /**
    * Removes mass from parent element.
    * @param mass mass removed
    */
  def del(mass: Mass)
}