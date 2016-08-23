package lars.core.celestial.body.standard

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.Physics
import lars.core.physics.units.{Length, Mass, Time, Velocity}

/**
  * Singularities are all types of black holes.  This includes miniature, stellar, intermediate, and super massive /
  * quasars.
  *
  * See: https://docs.google.com/document/d/1CbM0HG1Tb4D8JjgCdMcMY4PkaqaRTMqWDbzBZzniOFo/
  *
  * @param mass
  * @param location
  */
class Singularity(override var name: String,
                  override var mass: Mass,
                  override var location: Vec2,
                  override var velocity: Velocity,
                  override var parent: Parent)
  extends Sizeable
    with Child
    with Observable
    with Nameable {
  override var size: Length = Physics.schwarzschildRadius(mass)

  override def observe(time: Time): Unit = {}

  /**
    * This happens when an object falls into the event horizon of a blackhole.
    *
    * @param other
    */
  override def collide(other: Sizeable): Unit = {
    mass += other.mass
    size = Physics.schwarzschildRadius(mass)
  }
}
