package lars.core.celestial

import lars.core.physics.units.Velocity

/**
  * TemporalMassive objects are massive objects that exist in time.  They add velocity to massives and a mechanism to
  * drift.  Drift is the mechanism that will allow objects to be moved by gravitational acceleration.
  */
trait TemporalMassive extends Massive {
  var velocity: Velocity
}
