package lars.game.engine.physics.units

import lars.game.engine.physics.units.Force.ForceType

class Force(val N: ForceType) {

}

object Force {
  type ForceType = Double
}