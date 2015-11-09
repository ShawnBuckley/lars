package lars.game.engine.celestial.body.base

import lars.game.engine.celestial.{Parent, Child, Massive, Sizeable}
import lars.game.engine.math.{Sphere, Vector2}
import lars.game.engine.physics.units.{Density, Length, Mass}

/**
  * Celestial bodies are an abstract base class used for the creation of other objects.  These include the standard
  * bodies: micro, gaseous, terrestrial, stellar, and singularity bodies.
  * Celestial bodies are standard (massive and sizeable) objects.
  *
  * See: https://docs.google.com/document/d/1uG7m4KMdTkZvW4E3ZjkSSvqP9JJVgQFqXzz1hyGJf7s
  *
  * @param mass
  * @param location
  * @param size
  */
abstract class CelestialBody(override var mass: Mass,
                    override var location: Vector2,
                    override var size: Length,
                    override var parent: Parent) extends Sizeable with Child