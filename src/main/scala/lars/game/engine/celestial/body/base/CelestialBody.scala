package lars.game.engine.celestial.body.base

import lars.game.engine.celestial.{Child, Massive, Sizeable}
import lars.game.engine.math.{Sphere, Vector2}
import lars.game.engine.physics.{Density, Length, Mass}

/**
  * Celestial bodies are an abstract base class used for the creation of other objects.  These include stellar, gaseous,
  * terrestrial, and micro bodies.  Celestial bodies are standard (massive and sizeable) objects
  *
  * @param _mass
  * @param _loc
  * @param _size
  */
abstract class CelestialBody(_mass: Mass, _loc: Vector2, _size: Length) extends Massive with Sizeable with Child {
  var loc = _loc
  var vel = Vector2.addIdent

  override def mass: Mass = _mass

  override def location: Vector2 = loc

  override def location_=(loc: Vector2) = this.loc = loc

  override def size: Length = _size

  override def drift_=(vec: Vector2): Unit = vel = vec

  override def drift: Vector2 = vel

  override def density: Density = new Density(mass.kg / Sphere.volume(size.km))
}
