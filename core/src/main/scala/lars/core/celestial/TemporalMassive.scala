package lars.core.celestial

import lars.core.physics.BarnesHutTree
import lars.core.physics.units.{Time, Velocity}

/**
  * TemporalMassive objects are massive objects that exist in time.  They add velocity to massives and a mechanism to
  * drift.  Drift is the mechanism that will allow objects to be moved by gravitational acceleration.
  */
trait TemporalMassive extends Massive {
  var velocity: Velocity

  def update(tree: BarnesHutTree, time: Time): Unit = {
    velocity += tree.root.calculate(this, time)
    location += (velocity * time.s).kms
  }
}
