package lars.controllers

import javax.ws.rs._

import lars.game.engine.celestial.Massive
import lars.Game
import org.json.{JSONArray, JSONObject}

@Path("/system")
@Produces(Array[String]("application/json"))
class PlanetController {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): String = {
    println("get planets")
    PlanetController.write(name)
  }
}

object PlanetController {
  def write(name: String): String = {
    println("write planets")
    val result = new JSONArray
    Game.galaxy.getSystem(name).bodies.foreach((body: Massive) => {
      val planet = new JSONObject
      if(body.name != null) planet.put("name", body.name)
      val location = new JSONObject
      location.put("x", body.location.x)
      location.put("y", body.location.y)
      planet.put("location", location)
      result.put(planet)
    })
    result.toString
  }
}