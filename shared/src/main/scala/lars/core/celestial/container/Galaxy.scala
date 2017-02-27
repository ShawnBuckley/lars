package lars.core.celestial.container

import lars.core.math.Vec2

/**
  * The galaxy is the root element of all celestial systems.  A galaxy represents the full space that exists within one
  * instances of LARS.  All celestial objects exist within the galaxy and cannot escape it.
  * @param name galaxy name
  */
class Galaxy(name: Option[String]) extends System(name, Vec2.addIdent, None)
