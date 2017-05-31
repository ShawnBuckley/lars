package controllers.v1

import javax.inject._

import lars.core.observation.Observer
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class GameController @Inject()(observer: Observer) extends Controller {

//  def getGalaxy = Action {
//    Ok(JsonUtil.toJson(celestialMapper.get(CelestialMapper.galaxyId)))
//  }

  def date = Action {
    Ok(Json.toJson(observer.date.d))
  }

  def running = Action {
    Ok(observer.isRunning.toString)
  }

  def pause = Action {
    Ok(observer.pause().toString)
  }
}
