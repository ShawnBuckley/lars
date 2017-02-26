package lars.client

import lars.client.celestial.CelestialBody
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

import scala.scalajs.js

object JsonParser {
  def parseVec2(data: js.Dynamic): Vec2 = {
    Vec2(data.x.asInstanceOf[Double],
      data.y.asInstanceOf[Double])
  }

  def parseCelestialBody(data: js.Dynamic): CelestialBody = {
    CelestialBody(
      Some(data.name.asInstanceOf[String]),
      Mass(data.mass.kg.asInstanceOf[Double]),
      parseVec2(data.location),
      Velocity(parseVec2(data.velocity.ms)),
      Length(data.size.km.asInstanceOf[Double])
    )
  }
}
