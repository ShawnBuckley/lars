package controllers.v1

import javax.inject._

import dao.CelestialDao
import lars.core.celestial.container.System
import lars.core.observation.Observer
import play.api.mvc._
import util.JsonUtil

import scala.concurrent.ExecutionContext

@Singleton
class SystemController @Inject()(celestialDao: CelestialDao, observer: Observer)(implicit ec: ExecutionContext)
  extends Controller {

  def find(name: String): Action[AnyContent] = Action.async {
    celestialDao.findWithAncestors(celestialDao.query.withName(name).withKind("system")).map { results =>
      results.head match {
        case system: System =>
          celestialDao.save(observer.observe(system).asInstanceOf[System])
          Ok(JsonUtil.toJson(system)).as("application/json")
        case _ =>
          NotFound(s"No such system $name")
      }
    }
  }
}
