package dao.memory

import java.util.UUID

import dao.CelestialDao
import model.Celestial

import scala.collection.mutable

class MemoryCelestialDao extends CelestialDao {

  private val bodies = new mutable.HashMap[UUID, Celestial]()

  override def get(id: UUID): Option[Celestial] = {
    bodies.get(id)
  }

  override def getByParent(id: UUID): Seq[Celestial] = {
    bodies.values.filter(_.parent.contains(id)).toSeq
  }

  override def getByAncestor(id: UUID): Seq[Celestial] = {
    bodies.values.filter(_.ancestor match {
      case None => false
      case Some(ancestor) => ancestor == id
    }).toSeq
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

  override def save(celestial: Celestial): Unit = {
    celestial.id.foreach(id => bodies.put(id, celestial))
  }

  override def delete(id: UUID): Unit = {
    bodies.remove(id)
  }
}
