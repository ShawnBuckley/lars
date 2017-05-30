import java.util.UUID

import dao.CelestialDao
import lars.core.celestial.definition.{Definition, DefinitionOrdering}
import model.Celestial

class CelestialModelFactory(celestialDao: CelestialDao) {

  implicit val ordering = DefinitionOrdering

  def create(definition: Definition): Unit = {
    createBodies(definition, None, None)
  }

  def getLastObserved(kind: String): Option[Double] = {
    if(List[String]("galaxy", "system", "terrestrial").exists(_.equals(kind))) Some(0) else None
  }

  private def createBody(definition: Definition,
                         parent: Option[UUID],
                         ancestor: Option[UUID]): UUID = {
    val id = UUID.randomUUID
    celestialDao.save(Celestial(
      id,
      definition.rank,
      definition.`type`,
      Some(definition.name),
      if(definition.orbit != null) definition.orbit.length else 0L,
      0, // y
      definition.mass,
      Some(definition.radius),
      Some(0), // velX
      if(definition.orbit != null) Some(definition.orbit.speed.ms) else Some(0L),
      getLastObserved(definition.`type`),
      ancestor match {
        case Some(_) => parent
        case None => None
      },
      ancestor match {
        case Some(ancestorId) => ancestorId
        case None => id
      }
    ))
    id
  }

  private def createBodies(definition: Definition, parent: Option[UUID], ancestor: Option[UUID]): Unit = {
    definition.`type` match {
      case "galaxy" =>
        val id = createBody(definition, None, None)
        CelestialDao.galaxyId = id
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.sortWith(_.rank > _.rank).foreach(createBodies(_, Some(id), None))
        }
      case "system" =>
        val id = createBody(definition, parent, ancestor)
        val nextAncestor = ancestor match {
          case None => Some(id)
          case Some(_) => ancestor
        }
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.sortWith(_.rank > _.rank).foreach(createBodies(_, Some(id), nextAncestor))
        }
      case _ =>
        createBody(definition, parent, ancestor)
    }
  }
}