package lars.game.engine.celestial.container

import lars.game.engine.celestial.Parent
import lars.game.engine.math.Vector2

import scala.collection.mutable.ArrayBuffer

class Galaxy extends Parent {
  val systems = new ArrayBuffer[System]
  def hasSystem(vec: Vector2): Boolean =
    false

//  def getSystem(vec: Vector2): System =
//    new System()

  def addSystem(system: System): Unit = {
    systems.append(system)
  }

  def getSystem(name: String): System =
    systems.view.filter(_.name == name).head

  override def absoluteLocation(relative: Vector2): Vector2 =
    relative
}
