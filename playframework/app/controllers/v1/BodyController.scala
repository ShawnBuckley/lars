package controllers.v1

import javax.inject.{Inject, Singleton}

import dao.CelestialDao
import lars.core.celestial.Body
import lars.core.observation.Observer
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import mapper.JsonWrites

import scala.concurrent.ExecutionContext

@Singleton
class BodyController @Inject()(celestialDao: CelestialDao, observer: Observer)(implicit ec: ExecutionContext)
  extends Controller {

  implicit val writes: OWrites[Body] = JsonWrites.bodyWriter

  def find(name: String): Action[AnyContent] = Action.async {
    celestialDao.find(celestialDao.query.withName(name).withKind("body")).map { results =>
      results.head match {
        case body: Body =>
          Ok(Json.toJson(body))
        case _ =>
          NotFound(s"No such body $name")
      }
    }
  }
}
