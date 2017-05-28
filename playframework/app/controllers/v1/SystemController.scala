package controllers.v1

import javax.inject._

import lars.core.celestial.container.System
import lars.core.observation.Observer
import mapper.CelestialMapper
import play.api.mvc._
import util.JsonUtil

@Singleton
class SystemController @Inject()(celestialMapper: CelestialMapper, observer: Observer) extends Controller {
  def find(name: String) = Action {
    celestialMapper.findByName(name, "system") match {
      case Some(system: System) =>
        observer.observe(system)
        celestialMapper.save(system)
        Ok(JsonUtil.toJson(system)).as("application/json")
      case _ =>
        NotFound(s"No such system $name")
    }
  }
}
