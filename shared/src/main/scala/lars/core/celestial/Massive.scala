package lars.core.celestial

import lars.core.math.Vec2
import lars.core.physics.Physics
import lars.core.physics.units.{Force, Length, Mass, Velocity}

/**
  * The Massive object trait.  Massive objects are the fundamental objects that can exist in space.  They have mass and
  * location.  These properties are variable in nature.  Objects can lose mass due events such as massive mining,
  * celestial collisions, and the poaching of fusion material in x-ray binaries.
  *
  * Massive objects can also be non-existent meta-objects such as barycenters and celestial containers such as systems.
  */
trait Massive {
  var mass: Mass
  var location: Vec2

  /**
    * Calculates the amount of force each object receives from the other.
    * @param other other mass
    * @return the gravitation force the objects are exerting on each other
    */
  def gravForce(other: Massive): Force =
    Force((location - other.location).normalize * (Physics.G.m * mass.kg * other.mass.kg) / math.pow(new Length(location.distance(other.location).magnitude).m, 2))

  /**
    * Calculates the required velocity to escape this mass.
    * @param location location escaping from
    * @return escape velocity
    */
  def escapeVelocity(location: Vec2): Velocity =
    Physics.escapeVelocity(this, location)
}
