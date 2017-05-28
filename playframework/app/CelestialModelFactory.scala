import dao.CelestialDao
import lars.core.celestial.definition.Definition
import model.Celestial

class CelestialModelFactory(celestialDao: CelestialDao) {

  def create(definition: Definition): Unit = {
    createBodies(definition, 0L, None)
  }

  def getLastObserved(kind: String): Option[Double] = {
    if(List[String]("galaxy", "system", "terrestrial").exists(_.equals(kind))) Some(0) else None
  }

  private def createBody(definition: Definition,
                 parent: Long,
                 ancestor: Option[Long]): Long = {
    celestialDao.save(Celestial(
      None,
      definition.`type`,
      Some(definition.name),
      if(definition.orbit != null) definition.orbit.length else 0L,
      0, // y
      definition.mass,
      Some(definition.radius),
      Some(0), // velX
      if(definition.orbit != null) Some(definition.orbit.speed.ms) else Some(0L),
      getLastObserved(definition.`type`),
      Some(parent),
      ancestor
    ))
  }

  private def createBodies(definition: Definition, parent: Long, ancestor: Option[Long]): Unit = {
    definition.`type` match {
      case "galaxy" =>
        createBody(definition, 0L, None)
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.foreach(createBodies(_, 0, None))
        }
      case "system" =>
        val id = createBody(definition, parent, ancestor)
        val nextAncestor = ancestor match {
          case None => Some(id)
          case Some(_) => ancestor
        }
        if(definition.bodies != null && definition.bodies.nonEmpty) {
          definition.bodies.foreach(createBodies(_, id, nextAncestor))
        }
      case _ =>
        createBody(definition, parent, ancestor)
    }
  }
}
