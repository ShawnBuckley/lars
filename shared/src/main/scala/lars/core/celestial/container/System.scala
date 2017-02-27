package lars.core.celestial.container

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Massive, Parent, TemporalMassive}
import lars.core.math.Vec2
import lars.core.physics.celestial.gravitation.{ForceCalculator, PairWise}
import lars.core.physics.celestial.gravitation.barneshut.BarnesHutTree
import lars.core.physics.units.{Length, Mass, Time, Velocity}

import scala.collection.mutable.ArrayBuffer

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
class System(override var name: Option[String], override var location: Vec2, override var parent: Option[Parent])
  extends TemporalMassive
    with Parent
    with Child
    with Observable
    with Nameable {
  override var velocity: Velocity = Velocity.zero
  override var mass: Mass = Mass.zero
  private var bodies = new ArrayBuffer[Massive]

  def add(body: Massive): Unit = {
    bodies.append(body)
    mass += body.mass
  }

  def del(body: Massive): Unit = {
    bodies = bodies.diff(List(body))
    mass -= body.mass
  }

  override def add(mass: Mass): Unit = {
    this.mass += mass
    parent.foreach(_.add(mass))
  }

  override def del(mass: Mass): Unit = {
    this.mass -= mass
    parent.foreach(_.del(mass))
  }

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

  def getAll: Seq[Massive] = {
    bodies
  }

  override def observe(time: Time): Unit = {
    tick(time)
  }

  def tick(time: Time): Unit = {
    val forceCalc: ForceCalculator =
      if(bodies.length < 100)
        new PairWise(bodies)
      else
        new BarnesHutTree(bodies, new Length(bodies.maxBy(_.location.magnitude).location.magnitude))
    bodies.foreach(body => {
      body match {
        case temporalMassive: TemporalMassive => temporalMassive.update(forceCalc, time)
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
