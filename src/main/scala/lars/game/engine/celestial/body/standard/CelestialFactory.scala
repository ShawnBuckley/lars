package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.{Massive, Parent}
import lars.game.engine.celestial.container.System
import lars.game.engine.celestial.body.BodyDefinition
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Velocity

object CelestialFactory {
  def createBody(body: BodyDefinition, primary: Vector2, parent: Parent): Massive = {
    body.classification match {
      case "singularity" => {
        val result = new Singularity(
          body.mass,
          primary + new Vector2(body.orbit.radius.km,0),
          new Velocity(new Vector2(0, body.orbit.speed.ms)),
          parent)
        result.name = body.name
        result
      }
      case "stellar" => {
        val result = new StellarBody(
          body.mass,
          primary + new Vector2(body.orbit.radius.km,0),
          new Velocity(new Vector2(0, body.orbit.speed.ms)),
          body.radius,
          parent)
        result.name = body.name
        result
      }
      case "gaseous" => {
        val result = new GaseousBody(
          body.mass,
          primary + new Vector2(body.orbit.radius.km,0),
          new Velocity(new Vector2(0, body.orbit.speed.ms)),
          body.radius,
          parent)
        result.name = body.name
        result
      }
      case "terrestrial" => {
        val result = new TerrestrialBody(
          body.mass,
          primary + new Vector2(body.orbit.radius.km,0),
          new Velocity(new Vector2(0, body.orbit.speed.ms)),
          body.radius,
          parent)
        result.name = body.name
        result
      }
      case "micro" => {
        val result = new MicroBody(
          body.mass,
          primary + new Vector2(body.orbit.radius.km,0),
          new Velocity(new Vector2(0, body.orbit.speed.ms)),
          body.radius,
          parent)
        result.name = body.name
        result
      }
      case default => null
    }
  }

  def createBodies(bodies: List[BodyDefinition], primary: Vector2, system: System): Unit = {
    bodies.foreach((body: BodyDefinition) => {
      println("creating " + body.name)
      system.add(CelestialFactory.createBody(body, primary, system))
      if(body.satellites != null && body.satellites.nonEmpty) createBodies(body.satellites, new Vector2(body.orbit.radius.km, 0), system)
    })
  }
}
