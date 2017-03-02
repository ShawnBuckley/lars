package lars.core.celestial.container

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Massive, Parent, TemporalMassive}
import lars.core.math.Vec2
import lars.core.physics.celestial.gravitation.{ForceCalculator, PairWise}
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
class System(override var name: Option[String],
             override var location: Vec2,
             override var velocity: Velocity,
             override var parent: Option[Parent])
  extends TemporalMassive
    with Parent
    with Child
    with Observable
    with Nameable {
  override var mass: Mass = Mass.zero
  val bodies = new mutable.ArrayBuffer[Massive]

  /**
    * Adds a body to the system and updates the mass.
    * @param body body to add
    */
  def add(body: Massive): Unit = {
    bodies.append(body)
    mass += body.mass
  }

  /**
    * Removes a body from the system and updates the mass.
    * @param body body to remove
    */
  def del(body: Massive): Unit = {
    bodies -= body
    mass -= body.mass
  }

  /**
    * Triggered when a massive object enters a system. This needs to remove the object from the parent.
    *
    * @param massive massive that entered the system
    */
  override def enter(massive: Massive): Unit = {
    bodies += massive
    parent match {
      case None =>
      case Some(parent: Parent) =>
        parent.del(massive)
    }
  }

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.
    *
    * @param massive massive that escaped
    */
  override def escape(massive: Massive): Unit = {
    parent match {
      case None =>
      case Some(parent: Parent) =>
        bodies -= massive
        parent.add(massive)
    }
  }

/**
    *
    * @param query body name
    * @return first body that matches the name
    */
  def get(query: String): Option[Massive] = {
    bodies.find({
      case nameable: Nameable =>
        nameable.name match {
          case Some(name: String) => name.equals(query)
          case None => false
        }
      case _ => false
    })
  }

  override def observe(time: Time): Unit = {
    val forceCalc: ForceCalculator =
      if(bodies.length < 100)
        new PairWise(bodies)
      else
        new BarnesHutTree(bodies, new Length(bodies.maxBy(_.location.magnitude).location.magnitude))
    bodies.foreach(body => {
      body match {
        case body: TemporalMassive =>
          body.update(forceCalc, time)
          // TODO - escape velocity calculation. requires checking for escape velocity difference over a stable orbit
//          if(body.velocity >= escapeVelocity(body.location))
//            escape(body)
        case _ =>
      }
      body match {
        case observable: Observable => observable.observe(time)
        case _ =>
      }
    })
  }

  override def absoluteLocation(relative: Vec2): Vec2 = {
    parent match {
      case Some(parent: Parent) => parent.absoluteLocation(location + relative)
      case None => relative
    }
  }
}