package lars.client.celestial

import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

import scala.collection.mutable

class System(name: Option[String],
             mass: Mass,
             location: Vec2,
             velocity: Velocity,
             size: Length,
             val bodies: mutable.ArrayBuffer[CelestialBody]) extends CelestialBody(name, mass, location, velocity, size) {

  def find(name: String): Option[CelestialBody] = {
    var result: Option[CelestialBody] = None
    bodies.foreach({
      case system: System =>
        system.find(name) match {
          case None =>
          case Some(body: CelestialBody) => result = Some(body)
        }
      case body: CelestialBody =>
        if(body.name.getOrElse("") == name)
          result = Some(body)
    })
    result
  }
}
