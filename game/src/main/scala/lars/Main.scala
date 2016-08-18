package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.System
import lars.core.math.Vector2
import com.corundumstudio.socketio.listener._
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import lars.controllers.PlanetController
import lars.core.celestial.CelestialConstants

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
    config.getSocketConfig.setReuseAddress(true)
    val socketio = new SocketIOServer(config)
    socketio.addEventListener[Array[Byte]]("planets", classOf[Array[Byte]], new DataListener[Array[Byte]] {
      override def onData(client: SocketIOClient, data: Array[Byte], ackRequest: AckRequest): Unit = {
        client.sendEvent("planets", PlanetController.write("Sol"))
      }
    })
    socketio.start()

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Create system
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val system = new System(CelestialConstants.Sol.name, new Vector2(0,0), null)
    system.name = CelestialConstants.Sol.name

    Game.galaxy.addSystem(system)
    CelestialFactory.createBodies(CelestialConstants.Sol.primaries, null, system)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main loop
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    println("LARS Core started. Press enter to stop.")

    var runLoop = true

    (new Thread(new Runnable {
      override def run(): Unit = {
        Console.in.readLine()
        println("LARS Core stopping.")
        runLoop = false
      }
    })).start()

    while(runLoop) {
      if(paused) {
        Thread.sleep(1000)
      } else {
        system.observe()
      }
    }

    server.stop()
    socketio.stop()

    println("LARS Core stopped.")
  }
}
