package lars.game.engine.celestial.body

import lars.game.engine.celestial.body.standard.OrbitDefinition
import lars.game.engine.physics.units.{Length, Mass}

case class BodyDefinition(val name: String,
                          val classification: String,
                          val radius: Length,
                          val mass: Mass,
                          val orbit: OrbitDefinition,
                          val satellites: List[BodyDefinition]) {

}
