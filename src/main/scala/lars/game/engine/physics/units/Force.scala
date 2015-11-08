package lars.game.engine.physics.units

class Force(val mass: Mass, val accel: Acceleration) {
  val KgM2 = mass.kg * accel.m2
}
