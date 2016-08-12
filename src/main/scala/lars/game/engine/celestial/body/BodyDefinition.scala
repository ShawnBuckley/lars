package lars.game.engine.celestial.body

import lars.game.engine.celestial.body.BodyClassification.BodyClassification
import lars.game.engine.celestial.body.standard.OrbitDefinition
import lars.game.engine.physics.units.{Length, Mass}

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