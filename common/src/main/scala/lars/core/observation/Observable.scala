package lars.core.observation

import lars.core.physics.units.Time

/**
  * This trait is for objects that can be observed.  The game engine is lazy and will only update objects when the are
  * observed.  The specifics what happens during observation is determined by the object's subtype.  Generally
  * observation will notify economies to run and celestial bodies to update their surface.
  */
trait Observable {

  /**
    * Observe an object.  The time passed is the current simulation date.
    * @param date the current simulation time
    */
  def observed(date: Time): Unit

  var lastObserved: Time
}
