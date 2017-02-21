package lars.core.celestial.container

import lars.core.celestial.body.standard.StandardBody
import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Massive, Parent, TemporalMassive}
import lars.core.math.Vec2
import lars.core.physics.BarnesHutTree
import lars.core.physics.units.{Length, Mass, Time, Velocity}

import scala.collection.mutable.ArrayBuffer

/**
  * Systems are solar systems.  They will generally focus on a stellar primary or stellar binary+ primary.  There is no
  * requirement that the primary be a stellar object; anything sufficiently massive will work.  In rare instances some
  * systems can form around black holes and large collections of dark matter.
  *
  * The boundaries of a system are determined by the gravitational field of the system's total mass from the
  * barycenter.
  *
  * The motions of objects within systems are entirely driven by the interaction of gravitational fields.
  *
  * Observing a system will update the locations of all objects within it and observe all objects within it.
  *
  * @param location system location
  * @param parent system parent
  */
class System(override var name: Option[String], override var location: Vec2, override var parent: Parent)
  extends TemporalMassive
    with Parent
    with Child
    with Observable
    with Nameable {
  override var velocity: Velocity = Velocity.zero
  override var mass: Mass = Mass.zero
  private var bodies = new ArrayBuffer[StandardBody]

  def add(body: StandardBody): Massive = {
    bodies.append(body)
    mass += body.mass
    body
  }

  def del(body: StandardBody): Unit = {
    bodies = bodies.diff(List(body))
    mass -= body.mass
  }

  def get(query: String): Option[StandardBody] =
    bodies.find(body => {
      body.name match {
        case Some(name: String) => name.equals(query)
        case None => false
      }
    })

  def getAll: Seq[StandardBody] = {
    bodies
  }

  override def observe(time: Time): Unit = {
    tick(time)
  }

  def tick(time: Time): Unit = {
    val tree = new BarnesHutTree(bodies, new Length(Length.Km.au*50))
    bodies.foreach(body => {
      body.update(tree.root.calculate, time)
      body match {
        case observable: Observable => observable.observe(time)
        case _ =>
      }
    })
  }

  override def absoluteLocation(relative: Vec2): Vec2 =
    parent.absoluteLocation(location + relative)
}
