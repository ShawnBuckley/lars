package mapper

import com.google.inject.Inject
import dao.CelestialDao
import lars.core.Nameable
import lars.core.celestial._
import lars.core.celestial.body._
import lars.core.celestial.container._
import lars.core.math.Vec2
import lars.core.observation.Observable
import lars.core.physics.units.{Length, Mass, Time, Velocity}
import model.Celestial

class CelestialMapper @Inject()(celestialDao: CelestialDao) {

  def convertChildren(parent: System): Unit = {
    celestialDao.getByParent(parent.id.get)
      .toSeq
      .sortBy(model => Vec2(model.x, model.y).magnitude)
      .foreach(celestial => parent.add(convert(celestial, Some(parent)).get))
  }

  def convert(celestial: Celestial, parent: Option[Parent with Child]): Option[TemporalMassive with Child] = {
    val name = celestial.name
    val mass = Mass(celestial.mass)
    val radius = celestial.size match {
      case None => Length.zero
      case Some(length) => new Length(length)
    }
    val location = new Vec2(celestial.x, celestial.y)
    val velocity = new Velocity(new Vec2(celestial.velX, celestial.velY))

    celestial.kind match {
      case "galaxy" =>
        Some(new Galaxy(name))
      case "system" =>
        val result = Some(new System(celestial.id, name, location, velocity, new Time(celestial.lastObserved.get), parent))
        convertChildren(result.get)
        result
      case "micro" =>
        Some(new MicroBody(celestial.id, name, mass, location, velocity, radius, parent))
      case "gaseous" =>
        Some(new GaseousBody(celestial.id, name, mass, location, velocity, radius, parent))
      case "terrestrial" =>
        Some(new TerrestrialBody(celestial.id, name, mass, location, velocity, radius, new Time(celestial.lastObserved.get), parent))
      case "stellar" =>
        Some(new StellarBody(celestial.id, name, mass, location, velocity, radius, parent))
      case "singularity" =>
        Some(new Singularity(celestial.id, name, mass, location, velocity, parent))
      case _ =>
        None
    }
  }

  def get(id: Long): Option[Massive] = {
    celestialDao.get(id) match {
      case None => None
      case Some(celestial) => convert(celestial, None)
    }
  }

  def findByName(term: String): Option[Massive] = {
    celestialDao.findByName(term) match {
      case None => None
      case Some(celestial) => convert(celestial, None)
    }
  }

  def findByName(term: String, kind: String): Option[Massive] = {
    celestialDao.findByName(term, kind) match {
      case None => None
      case Some(celestial) => convert(celestial, None)
    }
  }

  def save(massive: Massive): Long = {
    def handle(body: TemporalMassive with Nameable with Child, kind: String, lastObserved: Option[Double]): Celestial = {
      val radius = massive match {
        case sizeable: Sizeable => Some(sizeable.size.km)
        case _ => None
      }
      Celestial(body.id, kind, body.name, body.location.x, body.location.y, body.mass.kg, radius, body.velocity.ms.x, body.velocity.ms.y, lastObserved,
        body.parent match {
          case None => 0L
          case Some(parent) => parent.id.get
        }, None)
    }

    def handleObservable(body: TemporalMassive with Nameable with Child with Observable, kind: String): Celestial = {
      handle(body, kind, Some(body.lastObserved.d))
    }

    def handleStandard(body: TemporalMassive with Nameable with Child, kind: String): Celestial = {
      handle(body, kind, None)
    }

    def handleSystem(system: System, kind: String): Long = {
      val id = celestialDao.save(handleObservable(system, kind))
      system.bodies.foreach(save(_))
      id
    }

    massive match {
      case body: Galaxy =>
        handleSystem(body, "galaxy")
      case body: System =>
        handleSystem(body, "system")
      case body: MicroBody =>
        celestialDao.save(handleStandard(body, "micro"))
      case body: GaseousBody =>
        celestialDao.save(handleStandard(body, "gaseous"))
      case body: TerrestrialBody =>
        celestialDao.save(handleObservable(body, "terrestrial"))
      case body: StellarBody =>
        celestialDao.save(handleStandard(body, "stellar"))
      case body: Singularity =>
        celestialDao.save(handleStandard(body, "singularity"))
    }
  }

  def delete(id: Long): Unit = {
    celestialDao.delete(id)
  }
}
