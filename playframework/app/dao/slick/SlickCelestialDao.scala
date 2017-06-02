package dao.slick

import java.util.UUID
import javax.inject.Inject

import dao.{CelestialDao, CelestialQuery}
import lars.core.Identity
import lars.core.celestial.Massive
import model.Celestial
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

class SlickCelestialDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(override implicit val ec: ExecutionContext)
  extends CelestialDao
     with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val celestials = TableQuery[CelestialTable]

  override def getRaw(id: UUID): Future[Option[Celestial]] =
    db.run(celestials.filter(_.id === id).result.headOption)

  override def findRaw(query: CelestialQuery): Future[Seq[Celestial]] =
    db.run(query.asInstanceOf[SlickCelestialQuery].query.result)

  override def updateRaw(celestial: Celestial): Future[Unit] =
    db.run(celestials.filter(_.id === celestial.id).update(celestial)).map { _ => () }

  override def updateRaw(celestials: Seq[Celestial]): Future[Unit] =
    Future.successful(celestials.foreach(celestial =>
      db.run(this.celestials.filter(_.id === celestial.id).update(celestial))))

  override def insertRaw(celestial: Celestial): Future[Unit] =
    db.run(celestials += celestial).map { _ => () }

  override def insertRaw(celestials: Seq[Celestial]): Future[Unit] =
    db.run(this.celestials ++= celestials).map { _ => () }

  override def delete(id: UUID): Future[Unit] =
    db.run(celestials.filter(_.id === id).delete).map { _ => () }

  override def delete(query: CelestialQuery): Future[Unit] =
    db.run(query.asInstanceOf[SlickCelestialQuery].query.delete).map { _ => () }

  override def query: CelestialQuery =
    new SlickCelestialQuery(celestials)

  private class CelestialTable(tag: Tag) extends Table[Celestial](tag, "CELESTIALS") {
    def id = column[UUID]("id", O.PrimaryKey)
    def rank = column[Int]("rank")
    def kind = column[String]("kind")
    def name = column[Option[String]]("name")
    def x = column[Double]("x")
    def y = column[Double]("y")
    def mass = column[Double]("mass")
    def size = column[Option[Double]]("size")
    def velX = column[Option[Double]]("velX")
    def velY = column[Option[Double]]("velY")
    def observed = column[Option[Double]]("observed")
    def parent = column[Option[UUID]]("parent")
    def ancestor = column[UUID]("ancestor")

    override def * : ProvenShape[Celestial] = (id, rank, kind, name, x, y, mass, size, velX, velY, observed, parent, ancestor) <>
      ((Celestial.apply _).tupled, Celestial.unapply)
  }

  private class SlickCelestialQuery(val query: Query[SlickCelestialDao.this.CelestialTable, SlickCelestialDao.this.CelestialTable#TableElementType, Seq])
    extends CelestialQuery {

    override def withId(id: UUID): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.id === id))

    override def withKind(kind: String): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.kind === kind))

    override def withName(name: String): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.name === name))

    override def withParent(parent: UUID): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.parent === parent))

    override def withAncestor(ancestor: UUID): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.ancestor === ancestor))


    override def withIds(ids: Seq[UUID]): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.id.inSet(ids)))

    override def withKinds(kinds: Seq[String]): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.kind.inSet(kinds)))

    override def withNames(names: Seq[String]): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.name.inSet(names)))

    override def withParents(parents: Seq[UUID]): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.parent.inSet(parents)))

    override def withAncestors(ancestors: Seq[UUID]): CelestialQuery =
      new SlickCelestialQuery(query.filter(_.ancestor.inSet(ancestors)))
  }
}
