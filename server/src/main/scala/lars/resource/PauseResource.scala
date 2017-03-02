package lars.resource

import javax.ws.rs._

import lars.Main

@Path("/game")
@Produces(Array[String]("text/plain"))
class PauseResource {
  @POST
  @Path("/pause")
  def togglePause(): Unit =
    Main.pause()
}