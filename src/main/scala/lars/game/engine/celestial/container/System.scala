package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.Types
import lars.game.engine.celestial.body.base.CelestialBody
import lars.game.engine.celestial.{Parent, Massive, Child}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass

import scala.collection.mutable.ArrayBuffer

class System(_loc: Vector2, parent: Parent) extends Massive with Parent with Child {
  override var par: Parent = parent
  var loc = _loc
  var vec = Vector2.addIdent

  val bodies = new ArrayBuffer[Massive]

  def add(body: CelestialBody): Unit =
    bodies.append(body)

  override def observe(): Unit =
    bodies.foreach(_.observe())

  override def mass: Mass =
    new Mass(bodies.foldLeft(Types.zeroMass)((sum, body) => body.mass.kg + sum))

  override def absoluteLocation(relative: Vector2): Vector2 =
    Game.galaxy.absoluteLocation(location + relative)

  override def location: Vector2 =
    loc

  override def location_=(loc: Vector2): Unit =
    this.loc = loc

  override def drift_=(vec: Vector2): Unit =
    this.vec = vec

  override def drift: Vector2 =
    vec
}
