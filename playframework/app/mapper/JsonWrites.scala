package mapper

import lars.core.celestial._
import lars.core.celestial.container.System
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonWrites {

  implicit val locationWrites: Writes[Vec2] = (
      (JsPath \ "x").write[Double] and
      (JsPath \ "y").write[Double]
    )(unlift(Vec2.unapply))

  implicit val velocityWriter: Writes[Velocity] = (
      (JsPath \ "x").write[Double] and
      (JsPath \ "y").write[Double]
    )(velocity => (velocity.ms.x, velocity.ms.y))

  implicit object MassWriter extends Writes[Mass] {
    override def writes(o: Mass): JsValue =
      Json.toJson(o.kg)
  }

  implicit object LengthWriter extends Writes[Length] {
    override def writes(o: Length): JsValue =
      Json.toJson(o.km)
  }

  implicit val bodyWriter: OWrites[Body] = (
      (JsPath \ "name").writeNullable[String] and
      (JsPath \ "mass").write[Mass] and
      (JsPath \ "size").writeNullable[Length] and
      (JsPath \ "location").write[Vec2] and
      (JsPath \ "velocity").writeNullable[Velocity]
    )(body => (body.name, body.mass, body.size, body.location, body.velocity))

  implicit val systemWriter: OWrites[System] = (
    (JsPath \ "name").writeNullable[String] and
      (JsPath \ "mass").write[Mass] and
      (JsPath \ "size").writeNullable[Length] and
      (JsPath \ "location").write[Vec2] and
      (JsPath \ "velocity").writeNullable[Velocity] and
      (JsPath \ "bodies").write[Seq[JsValue]]
    )(body => (body.name, body.mass, body.size, body.location, body.velocity,
      body.bodies.map {
        case body: Body => bodyWriter.writes(body)
        case system: System => systemWriter.writes(system)
      }))
}
