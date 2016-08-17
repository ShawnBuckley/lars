package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.celestial.{Child, Massive, Parent}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.Physics
import lars.game.engine.physics.units.{Mass, Velocity}

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
class System(override var location: Vector2, override var parent: Parent) extends Massive with Parent with Child {
  override var name: String = ""
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

  override def observe(): Unit = {
    tick()
  }

  def tick(): Unit = {
    (bodies, Physics.gravAcceleration(bodies)).zipped.map(((body: Massive, velocity: Velocity) => {
      body.velocity += velocity
      body.location += body.velocity.kms
      body.observe()
    })(_, _))
  }

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)
}
