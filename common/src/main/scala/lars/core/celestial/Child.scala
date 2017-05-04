package lars.core.celestial

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lars.core.Identity
import lars.core.math.Vec2

/**
  * This trait is used to determine a nested object's absolute location in the galaxy.
  */
@JsonIgnoreProperties(Array[String]("parent"))
trait Child extends NestedLocation with Identity {
  var parent: Option[Parent with Child]

  /**
    * Gets the eldest most parent.
    * @return eldest most parent
    */
  def ancestor: Option[Parent] = {
    parent match {
      case None => None
      case Some(parent) =>
        parent.parent match {
          case None => Some(parent)
          case Some(_) => parent.ancestor
        }

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
