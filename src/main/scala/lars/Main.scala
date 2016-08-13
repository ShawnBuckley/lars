package lars

import lars.game.engine.celestial.body.standard._
import lars.game.engine.celestial.container.System
import lars.game.engine.math.Vector2
import com.corundumstudio.socketio.listener._
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import lars.controllers.PlanetController
import lars.game.engine.celestial.CelestialConstants

object Main {
  var paused = false

  def main(args: Array[String]) {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Setup
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val server = new EmbeddedWebapp
    server.start()


    val config = new Configuration
    config.setHostname("localhost")
    config.setPort(9092)
    val socketio = new SocketIOServer(config)
    socketio.addEventListener[Array[Byte]]("planets", classOf[Array[Byte]], new DataListener[Array[Byte]] {
      override def onData(client: SocketIOClient, data: Array[Byte], ackRequest: AckRequest): Unit = {
        client.sendEvent("planets", PlanetController.write("Sol").toString)
      }
    })
    socketio.start()

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Create system
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val system = new System(new Vector2(0,0), null)
    system.name = CelestialConstants.Sol.name

    Game.galaxy.addSystem(system)
    CelestialFactory.createBodies(CelestialConstants.Sol.primaries, new Vector2(0,0), system)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main loop
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    while(true) {
      if(paused) {
        Thread.sleep(1000)
      } else {
        system.observe()
      }
    }

    socketio.stop()
  }
}
