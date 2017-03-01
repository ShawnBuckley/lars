package lars.client

import lars.client.celestial.CelestialBody
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

import scala.collection.mutable
import scala.scalajs.js

object JsonParser {
  def hasFields(data: js.Dynamic, fields: Seq[String]): Boolean = {
    fields.forall(!data.selectDynamic(_).isInstanceOf[Unit])
  }

  def parseVec2(data: js.Dynamic): Option[Vec2] = {
    if(!hasFields(data, Array("x", "y"))) {
      None
    } else {
      Some(Vec2(data.x.asInstanceOf[Double],
        data.y.asInstanceOf[Double]))
    }
  }

  def parseSystem(data: js.Dynamic, offset: Vec2): Option[Seq[CelestialBody]] = {
    if(!hasFields(data, Array("name", "mass", "location", "velocity", "bodies"))) {
      None
    } else {
      val bodies = data.bodies.asInstanceOf[js.Array[js.Dynamic]]
      val result = new mutable.ArrayBuffer[CelestialBody](bodies.length)
      val location = parseVec2(data.location).get + offset
      bodies.foreach(body => {
        parseSystem(body, location) match {
          case None =>
            parseCelestialBody(body, location) match {
              case None =>
              case Some(body: CelestialBody) =>
                result += body
            }
          case Some(bodies: Seq[CelestialBody]) =>
            result ++= bodies
        }
      })
      Some(result)
    }
  }

  def parseCelestialBody(data: js.Dynamic, offset: Vec2): Option[CelestialBody] = {
    if(!hasFields(data, Array("name", "mass", "location", "velocity", "size"))) {
      None
    } else {
      Some(CelestialBody(
        Some(data.name.asInstanceOf[String]),
        Mass(data.mass.kg.asInstanceOf[Double]),
        parseVec2(data.location).get + offset,
        Velocity(parseVec2(data.velocity.ms).get),
        Length(data.size.km.asInstanceOf[Double])
      ))
    }
  }
}
