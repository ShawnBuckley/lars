package lars.game.engine.celestial

import lars.game.engine.math.{Sphere, Vector2}
import lars.game.engine.physics.units.{Mass, Density, Length}

class Sizeable(mass: Mass, loc: Vector2, size: Length) extends Massive(mass, loc) {
  def radius: Length =
    size

  def density: Density =
    new Density(mass.kg / Sphere.volume(radius).km3)

  def collide(other: Sizeable, velocity: Vector2) =
    {}
}
