package lars.resource

import javax.ws.rs._
import javax.ws.rs.core.Response

import com.google.inject.Inject
import lars.core.Game
import util.JsonUtil

@Path("/game")
@Produces(Array[String]("application/json"))
class GameResource @Inject()(game: Game) {
  @GET
  @Path("/galaxy")
  def get(): Response = {
    Response.ok(JsonUtil.toJson(game.galaxy)).build
  }

  @GET
  @Path("/date")
  def date: Response = {
    Response.ok(JsonUtil.toJson(game.observer.date)).build
  }
}