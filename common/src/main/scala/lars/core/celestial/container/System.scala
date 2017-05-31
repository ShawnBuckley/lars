package lars.core.celestial.container

import lars.core.{ID, Identity, Nameable}
import lars.core.celestial._
import lars.core.math.{Circle, Polar2, Vec2}
import lars.core.observation.Observable
import lars.core.physics.celestial.gravitation.{BarycenterRemove, ForceCalculator, PairWise}
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
class System(override var id: ID = ID(),
             override var name: Option[String],
             override var location: Vec2,
             override var velocity: Option[Velocity],
             override var lastObserved: Time,
             override var parent: Option[Parent])
  extends Massive
    with Sizeable
    with Parent
    with Child
    with Observable
    with Drifting
    with Nameable
    with Identity {
  override var mass: Mass = Mass.zero
  val bodies = new mutable.ArrayBuffer[Massive with Child]

  override def size: Option[Length] = {
    if(bodies.isEmpty)
      None
    else
      Some(Length(bodies.maxBy(_.location.magnitude).location.magnitude))
  }

  override def size_=(length: Option[Length]): Unit = {}

  /**
    * Adds a body to the system and updates the mass.
    * @param massive body to add
    */
  def add(massive: Massive with Child): Unit = {
    bodies += massive
    mass += massive.mass
  }

  /**
    * Removes a body from the system and updates the mass.
    * @param massive body to remove
    * @return if body was removed
    */
  def del(massive: Massive with Child): Boolean = {
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
  override def enter(massive: Massive with Child): Unit = {
    add(massive)

    // Update drift if it has drift
    massive match {
      case drifting: Drifting =>
        drifting.velocity.foreach(drift => velocity.foreach(vel => drifting.velocity = Some(drift - vel)))
      case _ =>
    }

    massive.location -= location

    parent.foreach(_.del(massive))
    massive.parent = Some(this)
  }

  /**
    * Triggers of moving a child element to the container's parent when the child exceeds the escape velocity.  Does
    * nothing when there is no parent to escape into.
    * @param massive massive that escaped
    */
  override def escape(massive: Massive with Child): Unit = {
    if(bodies.contains(massive)) {
      bodies -= massive
      mass -= massive.mass
      massive.location += location

      // Update drift if it has drift
      massive match {
        case drifting: Drifting =>
          drifting.velocity.foreach(drift => velocity.foreach(vel => drifting.velocity = Some(drift + vel)))
        case _ =>
      }

      parent.foreach(_.enter(massive))
    }
  }

  /**
    * Returns children of the parent
    * @return children
    */
  override def children: Seq[Massive with Child] =
    bodies

  override def find(query: String): Option[Massive with Child] = {
    val result = bodies.find({
      case nameable: Nameable =>
        nameable.name match {
          case Some(name: String) => name.equals(query)
          case None => false
        }
      case _ => false
    })
    if(result.isEmpty) {
      def findChild(system: Massive with Child): Option[Massive with Child] = {
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
  def get(query: String): Option[Massive with Child] = {
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
      case body: Massive with Drifting =>
        body.update(forceCalc, date - lastObserved)
        // TODO - escape velocity calculation. requires checking for escape velocity difference over a stable orbit
//        if(body.velocity >= escapeVelocity(body.location))
//          escape(body)
      case _ =>
    })
  }

  override def update(calculator: ForceCalculator, time: Time): Unit = {
    val barycenter = calculator.barycenter

    velocity.foreach(drift => {
      // TODO - preserve angular momentum
      velocity = Some(drift + calculator.calculate(this) / mass / time)
      if(drift.ms.magnitude != 0) {
        val distance = Length(barycenter.location.distance(location).magnitude)
        val traversed = Length((drift * time).magnitude)
        val angle = Circle.centralAngle(distance, traversed)
        val polar = Polar2.convert(barycenter.location, location)
        location = Polar2(polar.angle + angle, polar.length).cartesian(barycenter.location)
      }
    })
  }

  override def absoluteLocation(relative: Vec2): Vec2 = {
    parent match {
      case Some(parent: Parent) => parent.absoluteLocation(location + relative)
      case None => relative
    }
  }

  override def rank(child: Massive with Child): Option[Int] = {
    val index = bodies.indexOf(child)
    if(index < 0) None else Some(index)
  }
}
