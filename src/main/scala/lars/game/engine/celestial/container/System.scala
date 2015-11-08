package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.Types
import lars.game.engine.celestial.{Massive, Parent}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

import scala.collection.mutable.ArrayBuffer

class System extends Massive with Parent {
  var bodies = new ArrayBuffer[Massive]

  def generate(): Unit = {

  }

  def observe(): Unit =
    bodies.foreach(_.observe())

  override def mass: Mass =
    new Mass(bodies.foldLeft(Types.zeroMass)((sum, body) => body.mass.kg + sum))

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)

  override def location: Vector2 =
    ???

  override def location_=(loc: Vector2): Unit =
    ???

  override def drift_=(vec: Vector2): Unit =
    ???

  override def drift: Vector2 =
    ???
}
