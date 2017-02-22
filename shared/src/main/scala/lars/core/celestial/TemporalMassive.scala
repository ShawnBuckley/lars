package lars.core.celestial

import lars.core.math.{Circle, Polar2, Vec2}
import lars.core.physics.{Barycenter, Physics}
import lars.core.physics.units.{Force, Length, Time, Velocity}

/**
  * TemporalMassive objects are massive objects that exist in time.  They add velocity to massives and a mechanism to
  * drift.  Drift is the mechanism that will allow objects to be moved by gravitational acceleration.
  */
trait TemporalMassive extends Massive {
  var velocity: Velocity

  /**
    * Calculates the required velocity to escape this body.
    * @param location location escaping from
    * @return escape velocity
    */
  def escapeVelocity(location: Vec2): Velocity = {
    Physics.escapeVelocity(this, location)
  }

  /**
    * Updates the location.
    *
    * @param barycenter barycenter of the system
    * @param netForces function to calculation the forces of bodies in the system
    * @param time duration of orbit calculation
    */
  def update(barycenter: Barycenter, netForces: (TemporalMassive) => Force, time: Time): Unit = {
    // TODO - preserve angular momentum
    velocity += netForces(this) / mass / time
    val distance = Length(barycenter.location.distance(location).magnitude)
    val traversed = Length((velocity * time).magnitude)
    val angle = Circle.centralAngle(distance, traversed)
    val polar = Polar2.convert(barycenter.location, location)
    location = Polar2(polar.angle + angle, polar.length).cartesian(barycenter.location)
  }
}
