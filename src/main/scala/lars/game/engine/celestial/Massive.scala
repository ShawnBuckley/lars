package lars.game.engine.celestial

import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Velocity, Mass}

/**
  * The Massive object trait.  Massive objects are the fundamental objects that can exist in space.  They have mass,
  * location, and drift.  All of these properties are variable in nature.  Objects can lose mass due events such as
  * massive mining, orbiting collisions, and the poaching of fusion material in x-ray binaries.  Objects also have a
  * changeable location property and can drift.  Drift is the mechanism that will allow objects to be moved by
  * gravitational acceleration.
  *
  * Massive objects are observable.  The game engine is lazy, objects will only be updated when observed.  The specifics
  * what happens during observation is determined by the object's subtype.  Generally observation will update the
  * locations objects plus update the body's surface.
  *
  * Some objects will inherit Massive without inheriting from Sizeable as well.  These will include containers such as
  * systems, as well as anomalies such as dark matter and hyperspace nodes.
  *
  */
trait Massive {
  var mass: Mass
  var location: Vector2
  var velocity: Velocity //= Velocity.zero

  def observe(): Unit
}
