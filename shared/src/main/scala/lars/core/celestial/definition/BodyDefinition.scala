package lars.core.celestial.definition

import lars.core.celestial.body.standard.OrbitDefinition
import lars.core.celestial.definition.BodyClassification.BodyClassification
import lars.core.physics.units.{Length, Mass}

case class BodyDefinition(name: String,
                          classification: BodyClassification,
                          radius: Length,
                          mass: Mass,
                          override val orbit: OrbitDefinition)
  extends CelestialDefinition