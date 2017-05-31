package lars.core.celestial

import lars.core.{ID, Identity, Nameable}
import lars.core.math.{Circle, Polar2, Sphere, Vec2}
import lars.core.observation.Observable
import lars.core.physics.celestial.gravitation.ForceCalculator
import lars.core.physics.units._
import lars.core.surface.Surface

case class Body(override var id: ID = ID(),
                override var name: Option[String],
                override var parent: Option[Parent],
                override var lastObserved: Time,
                override var mass: Mass,
                override var location: Vec2,
                override var size: Option[Length],
                var orbiting: Option[Orbiting],
                override var velocity: Option[Velocity],
                var surface: Option[Surface])
  extends Massive
     with Sizeable
     with Drifting
     with Nameable
     with Identity
     with Observable
     with Child {

  def density: Option[Density] = {
    size.map(radius => new Density(mass.kg / Sphere.volume(radius).km3))
  }

  def collide(other: Body): Unit = {
    surface.foreach(_.collide(other))
  }

  override def update(calculator: ForceCalculator, time: Time): Unit = {
    val barycenter = calculator.barycenter

    orbiting.foreach(orbit => {
      // TODO - check if force is great enough to unseat if orbit
    })

    velocity.foreach(drift => {
      // TODO - preserve angular momentum
      velocity = Some(drift + calculator.calculate(this) / mass / time)
      if(drift.ms.magnitude != 0) {
        val distance = Length(barycenter.location.distance(location).magnitude)
        val traversed = Length((drift * time).magnitude)
        val angle = Circle.centralAngle(distance, traversed)
        val polar = Polar2.convert(barycenter.location, location)
        location = Polar2(polar.angle + angle, polar.length).cartesian(barycenter.location)
      }
    })
  }

  override def observed(date: Time): Unit = {
    surface.foreach(_.observed(date))
  }

  override def absoluteLocation(relative: Vec2): Vec2 = {
    parent match {
      case Some(parent: Parent) => parent.absoluteLocation(location + relative)
      case None => relative
    }
  }
}
