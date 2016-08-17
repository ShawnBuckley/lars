package lars.controllers

import javax.ws.rs._

import lars.Game
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class PlanetController {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): String = {
    PlanetController.write(name)
  }
}

object PlanetController {
  def write(name: String): String =
    JsonUtil.toJson(Game.galaxy.getSystem(name).bodies)
}