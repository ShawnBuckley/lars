package lars.resource

import javax.ws.rs._

import lars.core.celestial.container.System
import lars.Game
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class SystemResource {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): String = {
    SystemResource.write(name) match {
      case Some(string: String) => string
      case None => ""
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