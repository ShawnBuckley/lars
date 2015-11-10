package lars.game.engine.physics.units

import lars.game.engine.physics.units.Force.ForceType

case class Force(N: ForceType) {

}

object Force {
  type ForceType = Double
}