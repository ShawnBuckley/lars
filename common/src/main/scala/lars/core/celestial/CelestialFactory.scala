package lars.core.celestial

import lars.core.celestial.body._
import lars.core.celestial.container.System
import lars.core.celestial.definition.Definition
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}

object CelestialFactory {
  def createBody(definition: Definition, parent: Parent with Child): Option[StandardBody] = {
    val name = Some(definition.name)
    val mass = Mass(definition.mass)
    val radius = Length(definition.radius)
    val location = new Vec2(definition.orbit.length, 0)
    val velocity = new Velocity(new Vec2(0, definition.orbit.speed.ms))
    definition.`type` match {
      case "singularity" =>
        Some(new Singularity(name, mass, location, velocity, Some(parent)))
      case "stellar" =>
        Some(new StellarBody(name, mass, location, velocity, radius, Some(parent)))
      case "gaseous" =>
        Some(new GaseousBody(name, mass, location, velocity, radius, Some(parent)))
      case "terrestrial" =>
        Some(new TerrestrialBody(name, mass, location, velocity, radius, Some(parent)))
      case "micro" =>
        Some(new MicroBody(name, mass, location, velocity, radius, Some(parent)))
      case _ =>
        None
    }
  }

  def createBodies(definition: Definition, parent: Parent with Child): Unit = {
    definition.`type` match {
      case "galaxy" =>
        definition.bodies.foreach(body => {
          if(body.bodies != null && body.bodies.nonEmpty) {
            createBodies(body, parent)
          }
        })
      case "system" =>
        val location = new Vec2(definition.orbit.length, 0)
        val velocity = new Velocity(new Vec2(0, definition.orbit.speed.ms))
        val system = new System(Some(definition.name), location, velocity, Some(parent))
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.foreach(body => {
            createBodies(body, system)
          })
        }
        parent.add(system)
      case _ =>
        createBody(definition, parent) match {
          case None =>
          case Some(body: StandardBody) =>
            parent.add(body)
        }
    }
  }
}
