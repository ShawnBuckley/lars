package lars.game.engine.celestial

import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Density, Length}

trait Sizeable {
  def size: Length

  def density: Density

  def collide(other: Sizeable, velocity: Vector2)
}
