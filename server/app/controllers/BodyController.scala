package controllers

import javax.inject.{Inject, Singleton}

import lars.core.celestial.Child
import lars.core.celestial.body.standard.StandardBody
import lars.core.celestial.container.Galaxy
import lars.core.observation.{Observable, Observer}
import play.api.mvc.{Action, Controller}
import util.JsonUtil

@Singleton
class BodyController @Inject()(galaxy: Galaxy, observer: Observer) extends Controller {
  def find(name: String) = Action {
    galaxy.find(name) match {
      case Some(child: Child) => child match {
        case body: StandardBody =>
          body match {
            case observable: Observable => observer.observe(observable)
            case _ =>
          }
          Ok(JsonUtil.toJson(body))
        case _ => NotFound(s"No such body $name")
      }
      case None => NotFound(s"No such body $name")
    }
  }
}