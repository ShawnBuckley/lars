package dao.memory

import java.util.UUID
import javax.inject.Inject

import dao.{CelestialDao, CelestialQuery}
import lars.core.Identity
import lars.core.celestial.Massive
import model.Celestial

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class MemoryCelestialDao @Inject()()(override implicit val ec: ExecutionContext) extends CelestialDao {
  private val bodies = new mutable.HashMap[UUID, Celestial]()

  override def get(id: UUID): Future[Option[Massive with Identity]] = {
    Future.successful(bodies.get(id).flatMap(_.convert()))
  }

  override def query: CelestialQuery = new MemoryCelestialQuery(bodies.values)

  override def findRaw(query: CelestialQuery): Future[Seq[Celestial]] = {
    Future.successful(query.asInstanceOf[MemoryCelestialQuery].query.toSeq)
  }

  override def find(query: CelestialQuery): Future[Seq[Massive with Identity]] = {
    Future.successful(query.asInstanceOf[MemoryCelestialQuery].query.toSeq.flatMap(_.convert()))
  }

  override def save(massive: Massive with Identity): Future[Unit] = {
    save(Celestial.convert(massive))
  }

  override def save(celestial: Celestial): Future[Unit] = {
    Future.successful(bodies.put(celestial.id, celestial))
  }

  override def save(celestials: Seq[Celestial]): Future[Unit] = {
    Future.successful(celestials.foreach(celestial => bodies.put(celestial.id, celestial)))
  }

  override def delete(id: UUID): Future[Unit] = {
    Future.successful(bodies.remove(id))
  }

  override def delete(query: CelestialQuery): Future[Unit] = {
    Future.successful(convert(query).foreach(celestial => bodies.remove(celestial.id)))
  }

  private def convert(query: CelestialQuery): Iterable[Celestial] =
    query.asInstanceOf[MemoryCelestialQuery].query


  private class MemoryCelestialQuery(val query: Iterable[Celestial]) extends CelestialQuery {
    override def withId(id: UUID): CelestialQuery =
      new MemoryCelestialQuery(query.filter(_.id == id))

    override def withKind(kind: String): CelestialQuery =
      new MemoryCelestialQuery(query.filter(_.kind == kind))

    override def withName(name: String): CelestialQuery =
      new MemoryCelestialQuery(query.filter(_.name.contains(name)))

    override def withParent(parent: UUID): CelestialQuery =
      new MemoryCelestialQuery(query.filter(_.parent.contains(parent)))

    override def withAncestor(ancestor: UUID): CelestialQuery =
      new MemoryCelestialQuery(query.filter(_.ancestor == ancestor))


    override def withIds(ids: Seq[UUID]): CelestialQuery =
      new MemoryCelestialQuery(query.filter(celestial => ids.contains(celestial.id)))

    override def withKinds(kinds: Seq[String]): CelestialQuery =
      new MemoryCelestialQuery(query.filter(celestial => kinds.contains(celestial.kind)))

    override def withNames(names: Seq[String]): CelestialQuery =
      new MemoryCelestialQuery(query.filter(celestial => names.contains(celestial.name.orNull)))

    override def withParents(parents: Seq[UUID]): CelestialQuery =
      new MemoryCelestialQuery(query.filter(celestial => parents.contains(celestial.parent.orNull)))

    override def withAncestors(ancestors: Seq[UUID]): CelestialQuery =
      new MemoryCelestialQuery(query.filter(celestial => ancestors.contains(celestial.ancestor)))
  }
}
