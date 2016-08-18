package lars.core.celestial

/**
  * This is a trait that celestial containers will inherit.  It allows for transforming relative interior coordinates to
  * the coordinate system that the container resides in.  This call propagates to the eldest container to get the
  * absolute coordinates.
  */
trait Parent extends NestedLocation