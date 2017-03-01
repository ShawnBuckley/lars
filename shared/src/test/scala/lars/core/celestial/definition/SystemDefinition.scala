package lars.core.celestial.definition

import lars.core.celestial.body.standard.OrbitDefinition

case class SystemDefinition(name: String,
                            bodies: Seq[CelestialDefinition],
                            override val orbit: OrbitDefinition)
  extends CelestialDefinition