package lars.core.celestial.container

import java.util.UUID

import lars.core.Identity
import lars.core.celestial.{Child, Massive, Parent}
import lars.core.math.Vec2
import lars.core.observation.Unobservable
import lars.core.physics.units.Mass

import scala.collection.mutable

/**
  * The galaxy is the root element of all celestial systems.  A galaxy represents the full space that exists within one
  * instances of LARS.  All celestial objects exist within the galaxy and cannot escape it.
  * @param name galaxy name
  */
class Galaxy(override var id: Option[UUID], val name: Option[String], override var mass: Mass) extends Massive with Parent with Identity with Unobservable {
  override var location: Vec2 = Vec2.addIdent
  val bodies = new mutable.ArrayBuffer[Massive with Child]()

  /**
    * Adds massive to the celestial container.
    *
    * @param massive massive to add
    */
  override def add(massive: Massive with Child): Unit =
    bodies += massive

  /**
    * Removes a body from the system and updates the mass.
    *
    * @param massive body to remove
    * @return if body was removed
    */
  override def del(massive: Massive with Child): Boolean = {
    if(bodies.contains(massive)) {
      bodies -= massive
      true
    } else false
  }

  /**
    * Triggered when a massive object enters a system. This needs to remove the object from the parent.
    *
    * @param massive massive that entered the system
    */
  override def enter(massive: Massive with Child): Unit =
    add(massive)

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.  Does
    * nothing when there is no parent to escape into.
    *
    * @param massive massive that escaped
    */
  override def escape(massive: Massive with Child): Unit =
    del(massive)

  /**
    * Returns children of the parent
    *
    * @return children
    */
  override def children: Seq[Child] = bodies

  /**
    * Searches all children nodes for a child.
    *
    * @param name body name
    * @return first match
    */
  override def find(name: String): Option[Child] = None

  /**
    * Returns the objects absolute location. This works by propagating the call up to the most elder parent and using
    * it's coordinate system.
    *
    * @param relative relative location
    * @return absolute location
    */
  override def absoluteLocation(relative: Vec2): Vec2 = relative
}