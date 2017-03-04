package controllers

import javax.inject._

import lars.core.Game
import play.api.mvc._
import util.JsonUtil

@Singleton
class GameController @Inject()(game: Game) extends Controller {
  def galaxy = Action {
    Ok(JsonUtil.toJson(game.galaxy))
  }

  def date = Action {
    Ok(JsonUtil.toJson(game.observer.date))
  }
}
