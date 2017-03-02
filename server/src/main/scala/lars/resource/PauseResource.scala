package lars.resource

import javax.ws.rs._

import com.google.inject.Inject
import lars.core.Game

@Path("/game")
@Produces(Array[String]("text/plain"))
class PauseResource @Inject()(game: Game) {
  @POST
  @Path("/pause")
  def togglePause(): Unit =
    game.pause()
}
