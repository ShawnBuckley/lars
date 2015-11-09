package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Massive, Child}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

import scala.collection.mutable.ArrayBuffer

class System(override var location: Vector2, override var parent: Parent) extends Massive with Parent with Child {
  override var mass: Mass = new Mass(Mass.zero)
  var bodies = new ArrayBuffer[Massive]

  def add(body: CelestialBody): Unit = {
    bodies.append(body)
    mass += body.mass
  }

  def del(body: CelestialBody): Unit = {
    bodies = bodies.diff(List(body))
    mass -= body.mass
  }

  override def observe(): Unit =
    bodies.foreach(_.observe())

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)
}
