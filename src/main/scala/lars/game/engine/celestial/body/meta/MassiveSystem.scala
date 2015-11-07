package lars.game.engine.celestial.body.meta

import lars.game.engine.celestial.{Child, Massive, NestedLocation, Parent}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.Physics
import lars.game.engine.physics.units.Mass

/**
 * This is a class that is used to create a virtual binary+ system of massive objects.  It takes in an array of massives
 * and calculates what the center point of the provided objects as.
 *
 * @param massives The components to make the system
 */
class MassiveSystem(_par: Parent, massives: Seq[Massive]) extends Massive with Child with NestedLocation {
  private var barycenter = Physics.barycenter(massives)
  private var vel = Vector2(0,0)

  override var par: Parent = _par

  /**
   * Returns the total mass of the system.  Note: it is possible to double the mass value in calculations when
   * calling this method and the mass of its components together.  Use just one approach to find the mass of the system.
   * @return total mass of the system
   */
  override def mass: Mass =
    new Mass(massives.foldLeft(0L)((sum, massive) => massive.mass.kg + sum))

  /**
   * Returns the barycenter for the system
   * @return system barycenter
   */
  override def location: Vector2 =
    barycenter

  /**
   * Moves all objects in the system to the new location.  Positions relative to each other are maintained.
   * @param loc new barycenter
   */
  override def location_=(loc: Vector2): Unit = {
    val diff = barycenter - loc
    barycenter = loc
    massives.foreach(_.location += diff)
  }

  /**
   * This observes all of the objects in the system.
   */
  override def observe(): Unit =
    massives.foreach(_.observe())

  /**
   * Returns the current velocity that the system is drifting
   * @return drift
   */
  override def drift: Vector2 =
    vel

  /**
   * Sets the drift for the system and all objects in it
   * @param vec new drift
   */
  override def drift_=(vec: Vector2): Unit = {
    vel = vec
    massives.foreach(_.drift = vec)
  }

  /**
   * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
   * it's coordinate system.
   * @param relative relative location
   * @return absolute location
   */
  override def absoluteLocation(relative: Vector2): Vector2 =
    parent.absoluteLocation(relative)
}
