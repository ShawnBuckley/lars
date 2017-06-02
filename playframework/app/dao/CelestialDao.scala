package dao

import java.util.UUID

import lars.core.Identity
import lars.core.celestial.Massive
import model.Celestial

import scala.concurrent.{ExecutionContext, Future}

trait CelestialDao {
  implicit val ec: ExecutionContext

  def get(id: UUID): Future[Option[Massive with Identity]] =
    getRaw(id).map(_.flatMap(_.convert()))

  def find(query: CelestialQuery): Future[Seq[Massive with Identity]] =
    findRaw(query).map(_.flatMap(_.convert()))

  def update(massive: Massive with Identity): Future[Unit] =
    updateRaw(Celestial.convert(massive))

  def update(massives: Seq[Massive with Identity]): Future[Unit] =
    Future.successful(massives.foreach(massive => Celestial.convert(massive)))

  def insert(massive: Massive with Identity): Future[Unit] =
    insertRaw(Celestial.convert(massive))

  def insert(massives: Seq[Massive with Identity]): Future[Unit] =
    Future.successful(massives.foreach(massive => insertRaw(Celestial.convert(massive))))

  def findWithAncestors(query: CelestialQuery): Future[Seq[Massive with Identity]] = {
    findRaw(query).flatMap { celestials =>
      findRaw(this.query.withAncestors(celestials.map(_.ancestor))).map { withAncestors =>
        Celestial.convert(withAncestors ++ celestials)
      }
    }
  }

  def query: CelestialQuery

  def getRaw(id: UUID): Future[Option[Celestial]]
  def findRaw(query: CelestialQuery): Future[Seq[Celestial]]

  def updateRaw(celestial: Celestial): Future[Unit]
  def updateRaw(celestials: Seq[Celestial]): Future[Unit]

  def insertRaw(celestial: Celestial): Future[Unit]
  def insertRaw(celestials: Seq[Celestial]): Future[Unit]

  def delete(id: UUID): Future[Unit]
  def delete(query: CelestialQuery): Future[Unit]
}

trait CelestialQuery {
  def withId(id: UUID): CelestialQuery
  def withKind(kind: String): CelestialQuery
  def withName(name: String): CelestialQuery
  def withParent(parent: UUID): CelestialQuery
  def withAncestor(ancestor: UUID): CelestialQuery

  def withIds(ids: Seq[UUID]): CelestialQuery
  def withKinds(kinds: Seq[String]): CelestialQuery
  def withNames(names: Seq[String]): CelestialQuery
  def withParents(parents: Seq[UUID]): CelestialQuery
  def withAncestors(ancestors: Seq[UUID]): CelestialQuery
}

object CelestialDao {
  var galaxyId: UUID = _
}