package controllers.v1

import javax.inject._

import lars.core.observation.Observer
import mapper.CelestialMapper
import play.api.mvc._
import util.JsonUtil

@Singleton
class GameController @Inject()(celestialMapper: CelestialMapper, observer: Observer) extends Controller {

  def getGalaxy = Action {
    Ok(JsonUtil.toJson(celestialMapper.get(CelestialMapper.galaxyId)))
  }

  def date = Action {
    Ok(JsonUtil.toJson(observer.date))
  }

  def running = Action {
    Ok(observer.isRunning.toString)
  }

  def pause = Action {
    Ok(observer.pause().toString)
  }
}
