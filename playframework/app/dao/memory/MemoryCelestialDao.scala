package dao.memory

import dao.CelestialDao
import model.Celestial

import scala.collection.mutable

class MemoryCelestialDao extends CelestialDao {

  private var idSeq = 0L
  private val bodies = new mutable.HashMap[Long, Celestial]()

  override def get(id: Long): Option[Celestial] = {
    bodies.get(id)
  }

  override def getByParent(id: Long): Iterable[Celestial] = {
    bodies.values.filter(_.parent == id)
  }

  override def getByAncestor(id: Long): Iterable[Celestial] = {
    bodies.values.filter(_.ancestor match {
      case None => false
      case Some(ancestor) => ancestor == id
    })
  }

  override def findByName(term: String): Option[Celestial] = {
    bodies.values.find(_.name match {
      case Some(name) => name.equals(term)
      case None => false
    })
  }

  override def findByName(term: String, kind: String): Option[Celestial] = {
    bodies.values.find(celestial => celestial.kind.equals(kind) && (celestial.name match {
      case Some(name) => name.equals(term)
      case None => false
    }))
  }

  override def save(celestial: Celestial): Long = {
    celestial.id match {
      case None =>
        val id = idSeq
        celestial.id = Some(id)
        bodies.put(id, celestial)
        idSeq += 1
        id
      case Some(long) =>
        bodies.put(long, celestial)
        long
    }
  }

  override def delete(id: Long): Unit = {
    bodies.remove(id)
  }
}
