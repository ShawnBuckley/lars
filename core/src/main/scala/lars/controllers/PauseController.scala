package lars.controllers

import javax.ws.rs._

import lars.Main

@Path("/game")
@Produces(Array[String]("text/plain"))
class PauseController {
  @POST
  @Path("/pause")
  def togglePause(): Unit = {
    Main.paused = !Main.paused
  }
}
