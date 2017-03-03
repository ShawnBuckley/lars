package lars.resource

import javax.ws.rs._
import javax.ws.rs.core.Response

import com.google.inject.Inject
import lars.core.Game
import lars.core.celestial.container.System
import lars.core.celestial.Massive
import lars.core.observation.Observable
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class SystemResource @Inject()(game: Game) {
  @GET
  @Path("/{name}")
  def get(@PathParam("name") name: String): Response = {
    game.galaxy.get(name) match {
      case Some(system: System) =>
        game.observer.observe(system)
        Response.ok(JsonUtil.toJson(system)).build
      case Some(body: Massive) =>
        body match {
          case observable: Observable => game.observer.observe(observable)
          case _ =>
        }
        Response.ok(JsonUtil.toJson(body)).build
      case None =>
        Response.status(Response.Status.NOT_FOUND).build
    }
  }
}