package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.Physics
import lars.game.engine.physics.units.Mass

/**
  * Singularities are all types of black holes.  This includes miniature, stellar, intermediate, and super massive /
  * quasars.
  *
  * See: https://docs.google.com/document/d/1CbM0HG1Tb4D8JjgCdMcMY4PkaqaRTMqWDbzBZzniOFo/
  *
  * @param mass
  * @param loc
  */
class Singularity(mass: Mass, loc: Vector2, parent: Parent) extends CelestialBody(mass, loc, Physics.schwarzschildRadius(mass), parent) {
  override def observe(): Unit =
    {}

  /**
    * This happens when an object falls into the event horizon of a blackhole.
    *
    * @param other
    * @param velocity
    */
  override def collide(other: Sizeable, velocity: Vector2): Unit =
    ???
}
