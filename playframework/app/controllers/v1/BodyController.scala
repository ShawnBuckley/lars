package controllers.v1

import javax.inject.{Inject, Singleton}

import lars.core.celestial.{Body, Child}
import lars.core.observation.Observer
import mapper.CelestialMapper
import play.api.mvc.{Action, Controller}
import util.JsonUtil

import scala.concurrent.ExecutionContext

@Singleton
class BodyController @Inject()(celestialMapper: CelestialMapper, observer: Observer)(implicit ec: ExecutionContext)
  extends Controller {

  def find(name: String) = Action {
    celestialMapper.findByName(name) match {
      case Some(child: Child) => child match {
        case body: Body =>
          observer.observe(body)
          Ok(JsonUtil.toJson(body)).as("application/json")
        case _ =>
          NotFound(s"No such body $name")
      }
      case None =>
        NotFound(s"No such body $name")
    }
  }
}
