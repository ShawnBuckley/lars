package lars.game.engine.celestial.body

import lars.game.engine.celestial.{Parent, Sizeable}
import lars.game.engine.math.Vector2

/**
  * Stellar bodies are stars of all varieties. This
  *
  *
  * See: https://docs.google.com/document/d/1XPz32qaK-0D5I7oc1eLFulBTn2nzxTo0b6mf65V0RLQ
  *
  * @param mass
  * @param loc
  * @param size
  */
class StellarBody(mass: Long, loc: Vector2, size: Long) extends CelestialBody(mass, loc, size) {
  override var par: Parent = _

  override def observe(): Unit = ???

  override def collide(other: Sizeable, velocity: Vector2): Unit = ???
}
