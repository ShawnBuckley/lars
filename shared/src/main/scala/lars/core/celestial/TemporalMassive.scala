package lars.core.celestial

import lars.core.math.{Circle, Polar2}
import lars.core.physics.celestial.gravitation.ForceCalculator
import lars.core.physics.units.{Length, Time, Velocity}

/**
  * TemporalMassive objects are massive objects that exist in time.  They add velocity to massives and a mechanism to
  * drift.  Drift is the mechanism that will allow objects to be moved by gravitational acceleration.
  */
trait TemporalMassive extends Massive {
  var velocity: Velocity

  /**
    * Updates the location.
    *
    * @param calculator function to calculation the forces of bodies in the system
    * @param time duration of orbit calculation
    */
  def update(calculator: ForceCalculator, time: Time): Unit = {
    val barycenter = calculator.barycenter

    // TODO - preserve angular momentum
    velocity += calculator.calculate(this) / mass / time
    val distance = Length(barycenter.location.distance(location).magnitude)
    val traversed = Length((velocity * time).magnitude)
    val angle = Circle.centralAngle(distance, traversed)
    val polar = Polar2.convert(barycenter.location, location)
    location = Polar2(polar.angle + angle, polar.length).cartesian(barycenter.location)
  }
}
