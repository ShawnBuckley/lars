package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.units._

import scala.collection.mutable.ArrayBuffer

object Physics {
  /**
    * The gravitational constant.
    */
  // TODO - use correct unit for this (Nm2/kg2)
  val G = new Length(6.67e-14)

  /**
    * Speed of light in a vacuum.
    */
  val C = new Speed(299792458)

  /**
    * The schwarzschild radius pre-calculated for 1kg as a performance optimization.
    */
  val schwarzschildFactor: Length = Length.in.m((2.0 * G.m) / math.pow(C.ms, 2))

  /**
    * Calculates the acceleration due to gravity a group of bodies have on each other
    * @param bodies list of bodies
    * @return list of acceleration vectors
    */
  def gravAcceleration(bodies: Seq[Massive], time: Time = Time.second): Seq[Velocity] = {
    val velocities = new ArrayBuffer[Velocity]
    velocities.sizeHint(bodies)
    bodies.foreach((body: Massive) => {
      var velocity = Velocity.zero
      bodies.foreach((other: Massive) => {
        if(body != other) velocity += other.gravForce(body) / body.mass / time
      })
      velocities.append(velocity)
    })
    velocities
  }

  /**
    * Calculates the required velocity to escape a massive body.
    * @param body body
    * @param location location escaping from
    * @return required escape velocity
    */
  def escapeVelocity(body: Massive, location: Vec2): Velocity = {
    val distance = body.location.distance(location)
    new Velocity(distance.normalize * Math.sqrt((Physics.G.km * 2 * body.mass.kg) / distance.magnitude))
  }
}
