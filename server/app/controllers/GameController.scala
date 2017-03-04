package controllers

import javax.inject._

import lars.core.celestial.container.Galaxy
import lars.core.observation.Observer
import play.api.mvc._
import util.JsonUtil

@Singleton
class GameController @Inject()(galaxy: Galaxy, observer: Observer) extends Controller {
  def getGalaxy = Action {
    Ok(JsonUtil.toJson(galaxy))
  }

  def date = Action {
    Ok(JsonUtil.toJson(observer.date))
  }

  def pause = Action {
    observer.pause()
    Ok("")
  }
}
