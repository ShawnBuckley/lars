package lars.core.celestial

/**
  * This is a trait that celestial containers will inherit.  It allows for transforming relative interior coordinates to
  * the coordinate system that the container resides in and for updating celestial parent mass on child changes.  This
  * call propagates to the eldest container to get the absolute coordinates.
  */
trait Parent extends NestedLocation {
  /**
    * Adds massive to the celestial container.
    * @param massive massive to add
    */
  def add(massive: Massive)

  /**
    * Removes massive from the celestial container.
    * @param massive massive to remove
    */
  def del(massive: Massive)

  /**
    * Triggered when a massive object enters a system. This needs to remove the object from the parent.
    * @param massive massive that entered the system
    */
  def enter(massive: Massive)

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.
    * @param massive massive that escaped
    */
  def escape(massive: Massive)
}