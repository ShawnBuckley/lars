package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.celestial.{NestedLocation, Parent, Massive}
import lars.game.engine.math
import lars.game.engine.math.Vector2

import scala.collection.mutable.ArrayBuffer

class System extends Massive with Parent {
  var bodies = new ArrayBuffer[Massive]

  def generate(): Unit = {

  }

  def observe(): Unit =
    bodies.foreach(_.observe())

  override def mass: Long =
    bodies.foldLeft(0L)((sum, body) => body + sum)

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)

  override def location: Vector2 = ???

  override def location_=(loc: Vector2): Unit = ???

  override def drift_=(vec: Vector2): Unit = ???

  override def drift: Vector2 = ???
}
