package lars.resource

import javax.ws.rs._

import lars.Game
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class SystemResource {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): String = {
    SystemResource.write(name)
  }
}

object SystemResource {
  def write(name: String): String =
    JsonUtil.toJson(Game.galaxy.getSystem(name).bodies)
}