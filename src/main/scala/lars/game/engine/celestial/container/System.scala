package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.celestial.{Parent, Massive}
import lars.game.engine.math.Vector2

import scala.collection.mutable.ArrayBuffer

class System extends Massive with Parent {
  var bodies = new ArrayBuffer[Massive]

  def generate(): Unit = {

  }

  def observe(): Unit = {

  }

  override def mass: Long =
    bodies.reduceLeft(_.mass + _.mass)

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)
}
