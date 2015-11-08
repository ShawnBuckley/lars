package lars.game.engine.physics.units

import lars.game.engine.physics.units.Force.ForceType

class Force(val mass: Mass, val accel: Acceleration) {
  val KgM2: ForceType = mass.kg * accel.ms2
}

object Force {
  type ForceType = Double
}