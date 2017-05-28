package lars.core.celestial

import lars.core.celestial.container.System
import lars.core.celestial.definition.Definition
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Time, Velocity}

object CelestialFactory {
  def createBody(definition: Definition, parent: Parent with Child): Option[Massive with Child] = {
    val name = Some(definition.name)
    val mass = Mass(definition.mass)
    val radius = Length(definition.radius)
    val location = new Vec2(definition.orbit.length, 0)
    val velocity = new Velocity(new Vec2(0, definition.orbit.speed.ms))
    definition.`type` match {
      case "body" =>
        Some(new Body(
          id = None,
          name = name,
          parent = Some(parent),
          lastObserved = Time.zero,
          mass = mass,
          location = location,
          size = Some(radius),
          orbiting = None,
          velocity = Some(velocity),
          surface = None
        ))
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
        val system = new System(None, Some(definition.name), location, Some(velocity), Time.zero, Some(parent))
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.foreach(body => {
            createBodies(body, system)
          })
        }
        parent.add(system)
      case _ =>
        createBody(definition, parent) match {
          case None =>
          case Some(body) =>
            parent.add(body)
        }
    }
  }
}
