package lars.core.celestial.body

import lars.core.celestial.body.BodyClassification.BodyClassification
import lars.core.celestial.body.standard.OrbitDefinition
import lars.core.physics.units.{Length, Mass}

case class BodyDefinition(val name: String,
                          val classification: BodyClassification,
                          val radius: Length,
                          val mass: Mass,
                          val orbit: OrbitDefinition,
                          val satellites: List[BodyDefinition]) {

}

object BodyClassification extends Enumeration {
  type BodyClassification = Value
  val singularity = Value("singularity")
  val stellar = Value("stellar")
  val gaseous = Value("gaseous")
  val terrestrial = Value("terrestrial")
  val micro = Value("micro")
}