package lars.core.celestial.container

import lars.core.celestial.Parent
import lars.core.math.Vec2

import scala.collection.mutable.ArrayBuffer

class Galaxy extends Parent {
  val systems = new ArrayBuffer[System]
  def hasSystem(vec: Vec2): Boolean =
    false

  def addSystem(system: System): Unit = {
    systems.append(system)
  }

  def getSystem(query: String): Option[System] =
    systems.find(system => {
      system.name match {
        case Some(name: String) => name.equals(query)
        case None => false
      }
    })

  override def absoluteLocation(relative: Vec2): Vec2 =
    relative
}
