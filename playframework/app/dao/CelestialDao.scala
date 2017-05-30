package dao

import java.util.UUID

import lars.core.Identity
import lars.core.celestial.Massive
import model.Celestial

import scala.concurrent.{ExecutionContext, Future}

trait CelestialDao {
  implicit val ec: ExecutionContext

  def get(id: UUID): Future[Option[Massive with Identity]]

  def query: CelestialQuery
  def find(query: CelestialQuery): Future[Seq[Massive with Identity]]
  def findRaw(query: CelestialQuery): Future[Seq[Celestial]]

  def findWithAncestors(query: CelestialQuery): Future[Seq[Massive with Identity]] = {
    findRaw(query).flatMap { celestials =>
      findRaw(this.query.withAncestors(celestials.map(_.ancestor))).map { withAncestors =>
        Celestial.convert(withAncestors ++ celestials)
      }
    }
  }

  def save(massive: Massive with Identity): Future[Unit]
  def save(celestial: Celestial): Future[Unit]
  def save(celestials: Seq[Celestial]): Future[Unit]

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