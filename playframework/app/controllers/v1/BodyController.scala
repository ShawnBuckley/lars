package controllers.v1

import javax.inject.{Inject, Singleton}

import dao.CelestialDao
import lars.core.Identity
import lars.core.celestial.{Body, Massive}
import lars.core.observation.Observer
import play.api.mvc.{Action, AnyContent, Controller}
import util.JsonUtil

import scala.concurrent.ExecutionContext

@Singleton
class BodyController @Inject()(celestialDao: CelestialDao, observer: Observer)(implicit ec: ExecutionContext)
  extends Controller {

  def find(name: String): Action[AnyContent] = Action.async {
    celestialDao.find(celestialDao.query.withName(name).withKind("body")).map { results =>
      results.head match {
        case body: Body =>
          celestialDao.save(observer.observe(body).asInstanceOf[Body])
          Ok(JsonUtil.toJson(body)).as("application/json")
        case _ =>
          NotFound(s"No such body $name")
      }
    }
  }
}
