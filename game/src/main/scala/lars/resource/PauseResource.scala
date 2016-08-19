package lars.resource

import javax.ws.rs._

import lars.Main

@Path("/rest/game")
@Produces(Array[String]("text/plain"))
class PauseResource {
  @POST
  @Path("/pause")
  def togglePause(): Unit = {
    Main.paused = !Main.paused
  }
}
