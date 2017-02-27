package lars.core.celestial

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lars.core.math.Vec2

/**
  * This trait is used to determine a nested object's absolute location in the galaxy.
  */
@JsonIgnoreProperties(Array[String]("parent"))
trait Child extends NestedLocation {
  var parent: Option[Parent]

  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    *
    * @param relative relative location
    * @return absolute location
    */
  def absoluteLocation(relative: Vec2): Vec2
}
