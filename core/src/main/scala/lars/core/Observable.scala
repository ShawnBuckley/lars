package lars.core

import lars.core.physics.units.Time

/**
  * This trait is for objects that can be observed.  The game engine is lazy and will only update objects when the are
  * observed.  The specifics what happens during observation is determined by the object's subtype.  Generally
  * observation will notify economies to run and celestial bodies to update their surface.
  */
trait Observable {
  def observe(time: Time): Unit
}
