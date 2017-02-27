package lars.core.celestial.body.standard

import lars.core.celestial.{Massive, Parent, TemporalMassive}
import lars.core.celestial.container.System
import lars.core.celestial.body.{BodyClassification, BodyDefinition}
import lars.core.math.Vec2
import lars.core.physics.units.Velocity

object CelestialFactory {
  def createBody(body: BodyDefinition, primary: StandardBody, parent: Parent): StandardBody = {
    val location = (if(primary != null) primary.location else Vec2.addIdent) + new Vec2(body.orbit.radius.km,0)
    val velocity = (if(primary != null) primary.velocity else Velocity.zero) + new Velocity(new Vec2(0, body.orbit.speed.ms))
    body.classification match {
      case BodyClassification.singularity => new Singularity(Some(body.name), body.mass, location, velocity, Some(parent))
      case BodyClassification.stellar => new StellarBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent))
      case BodyClassification.gaseous => new GaseousBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent))
      case BodyClassification.terrestrial => new TerrestrialBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent))
      case BodyClassification.micro => new MicroBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent))
      case BodyClassification.none => null
    }
  }

  def createBodies(bodies: List[BodyDefinition], primary: StandardBody, system: System): Unit = {
    bodies.foreach((body: BodyDefinition) => {
      val satellite = CelestialFactory.createBody(body, primary, system)
      if(satellite != null) system.add(satellite)
      if(body.satellites != null && body.satellites.nonEmpty) createBodies(body.satellites, satellite, system)
    })
  }
}
