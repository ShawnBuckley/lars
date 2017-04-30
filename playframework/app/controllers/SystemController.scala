package controllers

import javax.inject._

import lars.core.celestial.container.{Galaxy, System}
import lars.core.observation.Observer
import play.api.mvc._
import util.JsonUtil

@Singleton
class SystemController @Inject()(galaxy: Galaxy, observer: Observer) extends Controller {
  def find(name: String) = Action {
    galaxy.get(name) match {
      case Some(system: System) =>
        observer.observe(system)
        Ok(JsonUtil.toJson(system)).as("application/json")
      case _ =>
        NotFound(s"No such system $name")
    }
  }
}
