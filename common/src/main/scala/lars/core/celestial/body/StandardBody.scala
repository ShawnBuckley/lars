package lars.core.celestial.body

import lars.core.{Identity, Nameable}
import lars.core.celestial.{Child, Parent, Sizeable}
import lars.core.math.Vec2

abstract class StandardBody extends Sizeable with Child with Nameable with Identity {
  override def absoluteLocation(relative: Vec2): Vec2 = {
    parent match {
      case Some(parent: Parent) => parent.absoluteLocation(location + relative)
      case None => relative
    }
  }
}