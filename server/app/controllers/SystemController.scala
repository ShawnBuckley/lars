package controllers

import javax.inject._

import lars.core.Game
import lars.core.celestial.Massive
import lars.core.celestial.container.System
import lars.core.observation.Observable
import play.api.mvc._

import util.JsonUtil

@Singleton
class SystemController @Inject()(game: Game) extends Controller {
  def find(name: String) = Action {
    game.galaxy.get(name) match {
      case Some(system: System) =>
        game.observer.observe(system)
        Ok(JsonUtil.toJson(system))
      case Some(body: Massive) =>
        body match {
          case observable: Observable => game.observer.observe(observable)
          case _ =>
        }
        Ok(JsonUtil.toJson(body))
      case None =>
        NotFound(s"No such system $name")
    }
  }
}
