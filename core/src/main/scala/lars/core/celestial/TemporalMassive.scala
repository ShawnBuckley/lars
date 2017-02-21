package lars.core.celestial

import lars.core.math.Vec2
import lars.core.physics.{BarnesHutTree, Physics}
import lars.core.physics.units.{Time, Velocity}

/**
  * TemporalMassive objects are massive objects that exist in time.  They add velocity to massives and a mechanism to
  * drift.  Drift is the mechanism that will allow objects to be moved by gravitational acceleration.
  */
trait TemporalMassive extends Massive {
  var velocity: Velocity

  /**
    * Calculates the required velocity to escape this body.
    * @param location location escaping from
    * @return escape velocity
    */
  def escapeVelocity(location: Vec2): Velocity = {
    Physics.escapeVelocity(this, location)
  }

  def update(tree: BarnesHutTree, time: Time): Unit = {
    velocity += tree.root.calculate(this, time)
    location += (velocity * time.s).kms
  }
}
