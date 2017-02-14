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
    * Calculates the amount of force each object receives from the other.
    * @param m1 first mass
    * @param m2 second mass
    * @return the gravitation force the objects are exerting on each other
    */
  def gravForce(m1: Massive, m2: Massive): Force =
    Force((m1.location - m2.location).normalize * (G.m * m1.mass.kg * m2.mass.kg) / math.pow(new Length(m1.location.distance(m2.location).magnitude).m, 2))

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
        if(body != other) velocity += Physics.gravForce(other, body) / body.mass / time
      })
      velocities.append(velocity)
    })
    velocities
  }

  /**
    * Calculates the schwarzschild radius of a mass.  The schwarzschild radius is used to calculate the radius of the
    * event horizon of a black hole.
    * @param mass input mass
    * @return schwarzschild radius
    */
  def schwarzschildRadius(mass: Mass): Length = {
    new Length(schwarzschildFactor.km * mass.kg)
  }
}
