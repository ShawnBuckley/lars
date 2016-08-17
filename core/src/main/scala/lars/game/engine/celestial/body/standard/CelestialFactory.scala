package lars.game.engine.celestial.body.standard

import lars.game.engine.celestial.{Massive, Parent}
import lars.game.engine.celestial.container.System
import lars.game.engine.celestial.body.{BodyClassification, BodyDefinition}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Velocity

object CelestialFactory {
  def createBody(body: BodyDefinition, primary: Massive, parent: Parent): Massive = {
    val location = (if(primary != null) primary.location else Vector2.addIdent) + new Vector2(body.orbit.radius.km,0)
    val velocity = (if(primary != null) primary.velocity else Velocity.zero) + new Velocity(new Vector2(0, body.orbit.speed.ms))
    body.classification match {
      case BodyClassification.singularity => {
        val result = new Singularity(body.mass, location, velocity, parent)
        result.name = body.name
        result
      }
      case BodyClassification.stellar => {
        val result = new StellarBody(body.mass, location, velocity, body.radius, parent)
        result.name = body.name
        result
      }
      case BodyClassification.gaseous => {
        val result = new GaseousBody(body.mass, location, velocity, body.radius, parent)
        result.name = body.name
        result
      }
      case BodyClassification.terrestrial => {
        val result = new TerrestrialBody(body.mass, location, velocity, body.radius, parent)
        result.name = body.name
        result
      }
      case BodyClassification.micro => {
        val result = new MicroBody(body.mass, location, velocity, body.radius, parent)
        result.name = body.name
        result
      }
    }
  }

  def createBodies(bodies: List[BodyDefinition], primary: Massive, system: System): Unit = {
    bodies.foreach((body: BodyDefinition) => {
      val satellite = CelestialFactory.createBody(body, primary, system)
      system.add(satellite)
      if(body.satellites != null && body.satellites.nonEmpty) createBodies(body.satellites, satellite, system)
    })
  }
}
