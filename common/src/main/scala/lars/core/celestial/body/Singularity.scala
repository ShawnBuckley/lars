package lars.core.celestial.body

import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.Physics
import lars.core.physics.units.{Length, Mass, Velocity}

/**
  * Singularities are all types of black holes.  This includes miniature, stellar, intermediate, and super massive /
  * quasars.
  *
  * See: https://docs.google.com/document/d/1CbM0HG1Tb4D8JjgCdMcMY4PkaqaRTMqWDbzBZzniOFo/
  * @param name object's name
  * @param mass initial mass
  * @param location initial location
  * @param velocity initial drift
  * @param parent parent container
  */
class Singularity(override var name: Option[String],
                  override var mass: Mass,
                  override var location: Vec2,
                  override var velocity: Velocity,
                  override var parent: Option[Parent with Child])
  extends StandardBody {
  override var size: Length = schwarzschildRadius

  /**
    * This happens when an object falls into the event horizon of a blackhole.
    * @param other other sizeable object
    */
  override def collide(other: Sizeable): Unit = {
    mass += other.mass
    size = schwarzschildRadius
    // TODO - destroy other object
  }

  /**
    * Calculates the schwarzschild radius of the singularity.  The schwarzschild radius is used to calculate the radius
    * of the event horizon.
    * @return schwarzschild radius
    */
  def schwarzschildRadius: Length =
    Singularity.schwarzschildRadius(mass)
}

object Singularity {
  def schwarzschildRadius(mass: Mass): Length = {
    new Length(Physics.schwarzschildFactor.km * mass.kg)
  }
}
