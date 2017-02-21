package lars.resource

import javax.ws.rs._
import javax.ws.rs.core.Response

import lars.core.celestial.container.System
import lars.Game
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class SystemResource {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): Response = {
    Game.galaxy.getSystem(name) match {
      case Some(system: System) => Response.ok(JsonUtil.toJson(system.getAll)).build
      case None => Response.status(Response.Status.NOT_FOUND).build
    }
  }
}

object SystemResource {
  def write(name: String): Option[String] =
    Game.galaxy.getSystem(name) match {
      case Some(system: System) => Some(JsonUtil.toJson(system.getAll))
      case None => None
    }
}