package lars.core.celestial.body.standard

import lars.core.celestial.Parent
import lars.core.celestial.container.System
import lars.core.celestial.definition.{BodyClassification, BodyDefinition, SystemDefinition}
import lars.core.math.Vec2
import lars.core.physics.units.Velocity

object CelestialFactory {
  def createBody(body: BodyDefinition, parent: Parent): Option[StandardBody] = {
    val location = new Vec2(body.orbit.radius.km,0)
    val velocity = new Velocity(new Vec2(0, body.orbit.speed.ms))
    body.classification match {
      case BodyClassification.singularity => Some(new Singularity(Some(body.name), body.mass, location, velocity, Some(parent)))
      case BodyClassification.stellar => Some(new StellarBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent)))
      case BodyClassification.gaseous => Some(new GaseousBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent)))
      case BodyClassification.terrestrial => Some(new TerrestrialBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent)))
      case BodyClassification.micro => Some(new MicroBody(Some(body.name), body.mass, location, velocity, body.radius, Some(parent)))
      case BodyClassification.none => None
    }
  }

  def createBodies(systemDefinition: SystemDefinition, system: System): Unit = {
    systemDefinition.bodies.foreach({
      case bodyDefinition: BodyDefinition =>
        CelestialFactory.createBody(bodyDefinition, system) match {
          case None =>
          case Some(body: StandardBody) =>
            system.add(body)
        }
      case systemDefinition: SystemDefinition =>
        val newSystem = new System(Some(systemDefinition.name), new Vec2(systemDefinition.orbit.radius.km, 0), Some(system))
        newSystem.velocity = new Velocity(new Vec2(0, systemDefinition.orbit.speed.ms))
        createBodies(systemDefinition, newSystem)
        system.add(newSystem)
    })
  }
}
