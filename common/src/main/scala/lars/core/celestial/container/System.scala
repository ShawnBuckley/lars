package lars.core.celestial.container

import com.fasterxml.jackson.annotation.JsonIgnore
import lars.core.{Identity, Nameable}
import lars.core.celestial.{Child, Parent, Sizeable, TemporalMassive}
import lars.core.math.Vec2
import lars.core.observation.Observable
import lars.core.physics.celestial.gravitation.{BarycenterRemove, ForceCalculator}
import lars.core.physics.celestial.gravitation.barneshut.BarnesHutTree
import lars.core.physics.units.{Length, Mass, Time, Velocity}

import scala.collection.mutable

/**
  * Systems are celestial systems.  They are any type of celestial system including: planet and moons, solar systems,
  * regions of space orbiting a primary, and even the galaxy itself.
  *
  * The boundaries of a system are determined by the gravitational field of the system's total mass from the
  * barycenter.
  *
  * The motions of objects within systems are entirely driven by the interaction of gravitational fields.  For
  * performance systems are considered to be isolated from other gravitational fields.  An object is considered to leave
  * a system when it exceeds escape velocity.
  *
  * Observing a system will update the locations of all objects within it and observe all objects within it.
  *
  * @param name optional system name
  * @param location system location
  * @param parent system parent
  */
class System(override var id: Option[Long],
             override var name: Option[String],
             override var location: Vec2,
             override var velocity: Velocity,
             override var lastObserved: Time,
             override var parent: Option[Parent with Child])
  extends Sizeable
    with Parent
    with Child
    with Observable
    with Nameable
    with Identity {
  override var mass: Mass = Mass.zero
  val bodies = new mutable.ArrayBuffer[TemporalMassive with Child with Identity]

  override def size: Length = {
    if(bodies.isEmpty)
      Length.zero
    else
      new Length(bodies.maxBy(_.location.magnitude).location.magnitude)
  }

  override def collide(other: Sizeable): Unit = {}

  /**
    * Adds a body to the system and updates the mass.
    * @param massive body to add
    */
  def add(massive: TemporalMassive with Child with Identity): Unit = {
    bodies += massive
    mass += massive.mass
  }

  /**
    * Removes a body from the system and updates the mass.
    * @param massive body to remove
    * @return if body was removed
    */
  def del(massive: TemporalMassive with Child with Identity): Boolean = {
    if(!bodies.contains(massive))
      false
    else {
      bodies -= massive
      mass -= massive.mass
      true
    }
  }

  /**
    * Triggered when a massive object enters a system. This needs to remove the object from the parent.
    * @param massive massive that entered the system
    */
  override def enter(massive: TemporalMassive with Child with Identity): Unit = {
    add(massive)
    massive.location -= location
    massive.velocity -= velocity
    parent match {
      case None =>
      case Some(parent: Parent) =>
        parent.del(massive)
    }
    massive.parent = Some(this)
  }

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.  Does
    * nothing when there is no parent to escape into.
    * @param massive massive that escaped
    */
  override def escape(massive: TemporalMassive with Child with Identity): Unit = {
    if(bodies.contains(massive)) {
      parent match {
        case None =>
        case Some(parent: Parent) =>
          bodies -= massive
          mass -= massive.mass
          massive.location += location
          massive.velocity += velocity
          parent.enter(massive)
      }
    }
  }

  /**
    * Returns children of the parent
    * @return children
    */
  @JsonIgnore override def children: Seq[Child] =
    bodies

  override def find(query: String): Option[Child] = {
    val result = bodies.find({
      case nameable: Nameable =>
        nameable.name match {
          case Some(name: String) => name.equals(query)
          case None => false
        }
      case _ => false
    })
    if(result.isEmpty) {
      def findChild(system: TemporalMassive with Child): Option[Child] = {
        system match {
          case parent: Parent =>
            parent.find(query) match {
              case Some(child: Child) => Some(child)
              case _ => None
            }
          case _ => None
        }
      }
      bodies.flatMap(findChild).headOption
    }
    else
      result
  }

  /**
    *
    * @param query body name
    * @return first body that matches the name
    */
  def get(query: String): Option[TemporalMassive with Child with Identity] = {
    bodies.find({
      case nameable: Nameable =>
        nameable.name match {
          case Some(name: String) => name.equals(query)
          case None => false
        }
      case _ => false
    })
  }

  override def observed(date: Time): Unit = {
    val forceCalc: ForceCalculator =
      if(bodies.length < 100)
        new BarycenterRemove(bodies)
      else
        new BarnesHutTree(bodies, new Length(bodies.maxBy(_.location.magnitude).location.magnitude))
    bodies.foreach({
      case body: TemporalMassive =>
        body.update(forceCalc, date - lastObserved)
        // TODO - escape velocity calculation. requires checking for escape velocity difference over a stable orbit
//        if(body.velocity >= escapeVelocity(body.location))
//          escape(body)
      case _ =>
    })
  }


  override def absoluteLocation(relative: Vec2): Vec2 = {
    parent match {
      case Some(parent: Parent) => parent.absoluteLocation(location + relative)
      case None => relative
    }
  }
}
