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
      case BodyClassification.singularity => new Singularity(body.name, body.mass, location, velocity, parent)
      case BodyClassification.stellar => new StellarBody(body.name, body.mass, location, velocity, body.radius, parent)
      case BodyClassification.gaseous => new GaseousBody(body.name, body.mass, location, velocity, body.radius, parent)
      case BodyClassification.terrestrial => new TerrestrialBody(body.name, body.mass, location, velocity, body.radius, parent)
      case BodyClassification.micro => new MicroBody(body.name, body.mass, location, velocity, body.radius, parent)
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
