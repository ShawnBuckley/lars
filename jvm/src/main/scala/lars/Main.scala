package lars

import scala.collection.JavaConversions._
import lars.core.celestial.body.standard._
import lars.core.celestial.container.System
import lars.core.math.Vec2
import com.corundumstudio.socketio.listener._
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import lars.application.LARSApplication
import lars.core.celestial.CelestialConstants
import lars.core.physics.units.Time
import lars.resource.SystemResource

object Main {
  var paused = false

  def main(args: Array[String]) {

    val systemName = "Sol"

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Setup
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    new LARSApplication().run("server", "game/src/main/resources/config.yml")

    val config = new Configuration
    config.setHostname("localhost")
    config.setPort(9092)
    config.getSocketConfig.setReuseAddress(true)
    val socketio = new SocketIOServer(config)
    socketio.addEventListener[Array[Byte]]("planets", classOf[Array[Byte]], new DataListener[Array[Byte]] {
      override def onData(client: SocketIOClient, data: Array[Byte], ackRequest: AckRequest): Unit = {
        client.sendEvent("planets", SystemResource.write(systemName))
      }
    })
    socketio.start()

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Create system
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val sol = new System(CelestialConstants.Sol.name, new Vec2(0,0), null)
    Game.galaxy.addSystem(sol)
    CelestialFactory.createBodies(CelestialConstants.Sol.primaries, null, sol)

    val xygon = new System(CelestialConstants.Xygon.name, Vec2.addIdent, null)
    Game.galaxy.addSystem(xygon)
    CelestialFactory.createBodies(CelestialConstants.Xygon.primaries, null, xygon)


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main loop
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    println("LARS Core started. Press enter to stop.")

    var runLoop = true

    new Thread(new Runnable {
      override def run(): Unit = {
        Console.in.readLine()
        println("LARS Core stopping.")
        runLoop = false
      }
    }).start()

    new Thread(new Runnable {
      override def run(): Unit = {
        while(runLoop) {
          Thread.sleep(100)
          socketio.getAllClients.foreach((client: SocketIOClient) => {
            client.sendEvent("planets", SystemResource.write(systemName))
          })
        }

      }
    }).start()

    while(runLoop) {
//      Thread.sleep(1000)
//      if(!paused) system.observe()
      if(paused) Thread.sleep(1000)
      else {
        sol.observe(Time.minute)
//        xygon.observe(Time.second)
      }
    }

    socketio.stop()

    println("LARS Core stopped.")
  }
}
