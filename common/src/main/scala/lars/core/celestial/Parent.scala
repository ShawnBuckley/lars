package lars.core.celestial

import lars.core.Identity

/**
  * This is a trait that celestial containers will inherit.  It allows for transforming relative interior coordinates to
  * the coordinate system that the container resides in and for updating celestial parent mass on child changes.  This
  * call propagates to the eldest container to get the absolute coordinates.
  */
trait Parent extends NestedLocation with Identity {
  /**
    * Adds massive to the celestial container.
    * @param massive massive to add
    */
    def add(massive: Massive with Child)

  /**
    * Removes a body from the system and updates the mass.
    * @param massive body to remove
    * @return if body was removed
    */
    def del(massive: Massive with Child): Boolean
  /**
    * Triggered when a massive object enters a system. This needs to remove the object from the parent.
    * @param massive massive that entered the system
    */
    def enter(massive: Massive with Child)

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.  Does
    * nothing when there is no parent to escape into.
    * @param massive massive that escaped
    */
  def escape(massive: Massive with Child)

  /**
    * Returns children of the parent
    * @return children
    */
  def children: Seq[Massive with Child]

  /**
    * Searches all children nodes for a child.
    * @param name body name
    * @return first match
    */
  def find(name: String): Option[Massive with Child]

  /**
    * Returns the rank of a child if descended from the parent.
    * @param child child
    * @return optional rank
    */
  def rank(child: Massive with Child): Option[Int]
}