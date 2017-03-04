package controllers

import javax.inject._

import lars.core.celestial.Massive
import lars.core.celestial.container.{Galaxy, System}
import lars.core.observation.{Observable, Observer}
import play.api.mvc._
import util.JsonUtil

@Singleton
class SystemController @Inject()(galaxy: Galaxy, observer: Observer) extends Controller {
  def find(name: String) = Action {
    galaxy.get(name) match {
      case Some(system: System) =>
        observer.observe(system)
        Ok(JsonUtil.toJson(system))
      case Some(body: Massive) => body match {
        case observable: Observable => observer.observe(observable)
        case _ =>
      }
        Ok(JsonUtil.toJson(body))
      case None =>
        NotFound(s"No such system $name")
    }
  }
}
