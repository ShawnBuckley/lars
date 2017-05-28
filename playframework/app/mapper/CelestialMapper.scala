package mapper

import com.google.inject.Inject
import dao.CelestialDao
import lars.core.{Identity, Nameable}
import lars.core.celestial._
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
      .foreach(celestial =>
        convert(celestial, Some(parent)) match {
          case Some(massive) => massive match {
            case child: Child => child.parent match {
              case Some(parent) => parent.add(child)
              case None =>
            }
            case _ =>
          }
          case None =>
        })
  }

  def convert(celestial: Celestial, parent: Option[Parent]): Option[Massive] = {
    val name = celestial.name
    val mass = Mass(celestial.mass)
    val radius = celestial.size match {
      case None => None
      case Some(length) => Some(new Length(length))
    }
    val location = new Vec2(celestial.x, celestial.y)
    val velocity = celestial.velX match {
      case None => None
      case Some(x) => Some(new Velocity(new Vec2(x, celestial.velY.get)))
    }

    celestial.kind match {
      case "galaxy" =>
        Some(new Galaxy(celestial.id, celestial.name, Mass(celestial.mass)))
      case "system" =>
        val result = new System(celestial.id, name, location, velocity, new Time(celestial.observed.get), parent)
        convertChildren(result)
        Some(result)
      case "body" =>
        Some(Body(
          id = celestial.id,
          name = celestial.name,
          parent = parent,
          lastObserved = new Time(celestial.observed match {
            case Some(time) => time
            case None => 0L
          }),
          mass = mass,
          location = location,
          size = radius,
          orbiting = None,
          velocity = velocity,
          surface = None))
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

  def save(massive: Massive with Identity): Long = {
    def handle(body: Massive with Nameable with Child with Identity, kind: String, lastObserved: Option[Double]): Celestial = {

      val radius = massive match {
        case system: System => Some(system.size.km)
        case body: Body => body.size.map(_.km)
        case _ => None
      }

      var driftX: Option[Double] = None
      var driftY: Option[Double] = None

      massive match {
        case drifting: Drifting =>
          drifting.velocity match {
            case Some(drift) =>
              driftX = Some(drift.ms.x)
              driftY = Some(drift.ms.y)
            case None =>
          }
        case _ =>
      }

      Celestial(body.id, kind, body.name, body.location.x, body.location.y, body.mass.kg, radius, driftX, driftY, lastObserved,
        body.parent match {
          case Some(parent) => parent.id
          case None => None
        }, None)
    }

    def handleObservable(body: Massive with Nameable with Child with Identity with Observable, kind: String): Celestial = {
      handle(body, kind, Some(body.lastObserved.d))
    }

    def handleStandard(body: Massive with Nameable with Child with Identity, kind: String): Celestial = {
      handle(body, kind, None)
    }

    def handleSystem(system: System): Long = {
      val id = celestialDao.save(handleObservable(system, "system"))
      system.bodies.foreach(save(_))
      id
    }

    def handleGalaxy(galaxy: Galaxy): Long = {
      val id = celestialDao.save(Celestial(galaxy.id, "galaxy", galaxy.name, 0, 0, galaxy.mass.kg, None, None, None, None, None, None))
      galaxy.bodies.foreach(save(_))
      id
    }

    massive match {
      case body: Galaxy =>
        handleGalaxy(body)
      case body: System =>
        handleSystem(body)
      case body: Body =>
        celestialDao.save(handleStandard(body, "body"))
    }
  }

  def delete(id: Long): Unit = {
    celestialDao.delete(id)
  }
}
