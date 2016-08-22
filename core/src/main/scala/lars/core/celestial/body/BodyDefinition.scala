package lars.core.celestial.body

import lars.core.celestial.body.BodyClassification.BodyClassification
import lars.core.celestial.body.standard.OrbitDefinition
import lars.core.physics.units.{Length, Mass}

case class BodyDefinition(name: String,
                          classification: BodyClassification,
                          radius: Length,
                          mass: Mass,
                          orbit: OrbitDefinition,
                          satellites: List[BodyDefinition]) {

}

object BodyClassification extends Enumeration {
  type BodyClassification = Value
  val singularity = Value("singularity")
  val stellar = Value("stellar")
  val gaseous = Value("gaseous")
  val terrestrial = Value("terrestrial")
  val micro = Value("micro")
  val none = Value("none")
}