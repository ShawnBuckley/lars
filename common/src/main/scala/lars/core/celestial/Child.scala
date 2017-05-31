package lars.core.celestial

import lars.core.Identity
import lars.core.math.Vec2

/**
  * This trait is used to determine a nested object's absolute location in the galaxy.
  */
trait Child extends NestedLocation with Identity {
  var parent: Option[Parent]

  /**
    * Gets the eldest most parent.
    * @return eldest most parent
    */
  def ancestor: Option[Parent] = {
    parent match {
      case Some(parent) => parent match {
        case child: Child => child.ancestor
        case _ => Some(parent)
      }
      case None => None // orphaned object
    }
  }

  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    *
    * @param relative relative location
    * @return absolute location
    */
  def absoluteLocation(relative: Vec2): Vec2
}
