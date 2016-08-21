package lars.core.celestial.container

import lars.core.{Nameable, Observable}
import lars.core.celestial.{Child, Massive, Parent}
import lars.core.math.Vector2
import lars.core.physics.Physics
import lars.core.physics.units.{Mass, Time, Velocity}

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
  * @param location
  * @param parent
  */
class System(override var name: String, override var location: Vector2, override var parent: Parent)
  extends Massive
    with Parent
    with Child
    with Observable
    with Nameable {
  override var velocity: Velocity = Velocity.zero
  override var mass: Mass = Mass.zero
  private var bodies = new ArrayBuffer[Massive]

  def add(body: Massive): Massive = {
    bodies.append(body)
    mass += body.mass
    body
  }

  def del(body: Massive): Unit = {
    bodies = bodies.diff(List(body))
    mass -= body.mass
  }

  def get(name: String): Massive = {
    bodies.filter((body: Massive) => if(body.isInstanceOf[Nameable]) body.asInstanceOf[Nameable].name.equals(name) else false).head
  }

  def getAll(): Seq[Massive] = {
    bodies
  }

  override def observe(time: Time): Unit = {
    tick(time)
  }

  def tick(time: Time): Unit = {
    (bodies, Physics.gravAcceleration(bodies)).zipped.map(((body: Massive, velocity: Velocity) => {
      body.velocity += velocity
      body.location += body.velocity.kms
      if(body.isInstanceOf[Observable]) body.asInstanceOf[Observable].observe(time)
    })(_, _))
  }

  override def absoluteLocation(relative: Vector2): Vector2 =
    parent.absoluteLocation(location + relative)
}
