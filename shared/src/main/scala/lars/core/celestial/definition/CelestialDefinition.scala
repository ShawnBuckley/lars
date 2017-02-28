package lars.core.celestial.definition

import lars.core.celestial.body.standard.OrbitDefinition

trait CelestialDefinition {
  def orbit: OrbitDefinition
}