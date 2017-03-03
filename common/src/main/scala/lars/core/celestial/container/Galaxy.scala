package lars.core.celestial.container

import lars.core.math.Vec2
import lars.core.observation.SelfObservable
import lars.core.physics.units.Velocity

/**
  * The galaxy is the root element of all celestial systems.  A galaxy represents the full space that exists within one
  * instances of LARS.  All celestial objects exist within the galaxy and cannot escape it.
  * @param name galaxy name
  */
class Galaxy(name: Option[String]) extends System(name, Vec2.addIdent, Velocity.zero, None) with SelfObservable