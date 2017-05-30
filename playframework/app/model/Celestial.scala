package model

import java.util.UUID

import lars.core.{ID, Identity, Nameable}
import lars.core.celestial.container.{Galaxy, System}
import lars.core.celestial._
import lars.core.math.Vec2
import lars.core.observation.Observable
import lars.core.physics.units.{Length, Mass, Time, Velocity}

import scala.collection.mutable

case class Celestial(id: UUID,
                     kind: String,
                     name: Option[String],
                     x: Double,
                     y: Double,
                     mass: Double,
                     size: Option[Double],
                     velX: Option[Double],
                     velY: Option[Double],
                     observed: Option[Double],
                     parent: Option[UUID],
                     ancestor: UUID) {

  def convert(parent: Option[Parent] = None): Option[Massive with Identity] = {
    val radius = size match {
      case None => None
      case Some(length) => Some(new Length(length))
    }
    val location = new Vec2(x, y)
    val velocity = velX match {
      case None => None
      case Some(velX) => Some(new Velocity(new Vec2(velX, velY.get)))
    }

    kind match {
      case "body" => Some(Body(
          id = ID(id),
          name = name,
          parent = parent,
          lastObserved = new Time(observed.getOrElse(0L)),
          mass = Mass(mass),
          location = location,
          size = radius,
          orbiting = None,
          velocity = velocity,
          surface = None))
      case "system" => Some(new System(ID(id), name, location, velocity, new Time(observed.get), parent))
      case "galaxy" => Some(new Galaxy(ID(id), name, Mass(mass)))
      case _ => None
    }
  }
}

object Celestial {

  def convert(massive: Massive with Identity, ancestorId: Option[UUID] = None): Seq[Celestial] = {

    val bottomParentId = massive match {
      case child: Child =>
        child.parent match {
          case Some(parent) => Some(parent.id.get)
          case None => None
        }
      case _ => None
    }

    val bottomAncestorId = ancestorId match {
      case Some(id) => id
      case None =>
        massive match {
          case child: Child =>
            child.ancestor match {
              case Some(ancestor) => ancestor.id.get
              case None => massive.id.get
            }
          case _ => massive.id.get
        }
    }

    val velocity = massive match {
      case drifting: Drifting => drifting.velocity match {
        case Some(velocity) => (Some(velocity.ms.x), Some(velocity.ms.y))
        case None => (None, None)
      }
      case _ => (None, None)
    }

     Seq(Celestial(
      id = massive.id.get,
      kind = massive match {
        case _: Body => "body"
        case _: System => "system"
        case _: Galaxy => "galaxy"
        case _ => "unknown"
      },
      name = massive match {
        case nameable: Nameable => nameable.name
        case _ => None
      },
      massive.location.x,
      massive.location.y,
      massive.mass.kg,
      size = massive match {
        case system: System => Some(system.size.km)
        case body: Body => body.size.map(_.km)
        case _ => None
      },
      velX = velocity._1,
      velY = velocity._2,
      observed = massive match {
        case observable: Observable => Some(observable.lastObserved.d)
        case _ => None
      },
      parent = bottomParentId,
      ancestor = bottomAncestorId
    )) ++ (massive match {
       case parent: Parent =>
         parent.children.flatMap{
           case child: Massive with Child with Identity => Celestial.convert(child, Some(bottomAncestorId))
           case _ => None
         }
       case _ =>
         Seq.empty[Celestial]
     })
  }

  def convert(celestials: Seq[Celestial]): Seq[Massive with Identity] = {
    val eldest = new mutable.ArrayBuffer[Massive with Identity]
    val converted = new mutable.HashMap[UUID, Massive with Identity]
    val added = new mutable.HashSet[UUID]

    val celestialMap = new mutable.HashMap[UUID, Celestial]

    def getOrConvert(celestial: Celestial, parent: Option[Parent]): Option[Massive with Identity] = {
      converted.get(celestial.id) match {
        case Some(result) => Some(result)
        case None =>
          celestial.convert(parent).map { result =>
            converted.put(celestial.id, result)
            result
          }
      }
    }

    def convertElders(celestial: Celestial): Option[Massive with Identity] = {
      celestial.parent match {
        case None =>
          getOrConvert(celestial, None).map { result =>
            eldest += result
            result
          }
        case Some(parent) =>
          convertElders(celestialMap(parent)).flatMap{result =>
            val parent = result.asInstanceOf[Parent]
            getOrConvert(celestial, Some(parent)).map(result => {
              if(!added.contains(result.id.get)) {
                added.add(result.id.get)
                parent.add(result.asInstanceOf[Massive with Child])
              }
              result
            })
          }
      }
    }

    celestials.foreach(celestial => celestialMap.put(celestial.id, celestial))
    celestials.foreach(celestial => convertElders(celestial))

    eldest
  }
}