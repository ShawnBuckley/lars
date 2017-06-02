package lars.core.celestial

import lars.core.Identity
import lars.core.math.Vec2
import lars.core.physics.units.Velocity

trait Child extends Identity {
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

  def absoluteVelocity: Option[Velocity]

  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    * @return absolute location
    */
  def absoluteLocation: Vec2
}
