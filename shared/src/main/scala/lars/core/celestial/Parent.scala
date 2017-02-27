package lars.core.celestial

import lars.core.physics.units.Mass

/**
  * This is a trait that celestial containers will inherit.  It allows for transforming relative interior coordinates to
  * the coordinate system that the container resides in and for updating celestial parent mass on child changes.  This
  * call propagates to the eldest container to get the absolute coordinates.
  */
trait Parent extends NestedLocation {
  /**
    * Adds mass to the a celestial container.  Propegates to the eldest most celestial container.
    * @param mass mass to add
    */
  def add(mass: Mass)

  /**
    * Removes mass from a celestial container.  Propegates to the eldest most celestial container.
    * @param mass mass to remove
    */
  def del(mass: Mass)
}