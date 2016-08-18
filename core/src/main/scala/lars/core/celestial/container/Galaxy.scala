package lars.core.celestial.container

import lars.core.celestial.Parent
import lars.core.math.Vector2

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
