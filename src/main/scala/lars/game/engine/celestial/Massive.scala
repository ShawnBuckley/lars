package lars.game.engine.celestial

import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

/**
  * The Massive object class.  Massive objects are the fundamental objects that can exist in space.  They have mass,
  * location, and drift.  All of these properties are variable in nature.  Objects can lose mass due events such as
  * massive mining, orbiting collisions, and the poaching of fusion material in x-ray binaries.  Objects also have a
  * changeable location property and can drift.  Drift is the mechanism that will allow objects to be moved by
  * gravitational acceleration.
  *
  * Massive objects are observable.  The game engine is lazy, objects will only be updated when observed.  The specifics
  * what happens during observation is determined by the object's subtype.  Generally observation will update the
  * locations objects plus update the body's surface.
  *
  * @param mass
  * @param location
  * @param drift
  */
class Massive(var mass: Mass, var location: Vector2, var drift: Vector2 = Vector2.addIdent) {
  def observe(): Unit =
    {}
}
