package lars.game.engine.celestial.container

import lars.game.engine.celestial.Parent
import lars.game.engine.math.Vector2

class Galaxy extends Parent {
  def hasSystem(vec: Vector2): Boolean = false
//  def getSystem(vec: Vector2): System = new System()

  override def absoluteLocation(relative: Vector2): Vector2 =
    relative
}
