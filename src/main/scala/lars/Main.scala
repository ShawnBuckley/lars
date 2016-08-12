package lars

import lars.game.engine.Constants
import lars.game.engine.Constants.Body
import lars.game.engine.celestial.body.standard.{TerrestrialBody, StellarBody}
import lars.game.engine.celestial.container.System
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Velocity

import com.corundumstudio.socketio.listener._
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, Configuration, SocketIOServer}

object Main {
  val system = new System(new Vector2(0,0), null)
  system.name = "Sol"

  Game.galaxy.addSystem(system)

  var paused = false

  def main(args: Array[String]) {

    val server = new EmbeddedWebapp
    server.start()

    val config = new Configuration
    config.setHostname("localhost")
    config.setPort(9092)
    val socketio = new SocketIOServer(config)
    socketio.addEventListener[Array[Byte]]("planets", classOf[Array[Byte]], new DataListener[Array[Byte]] {
      override def onData(client: SocketIOClient, data: Array[Byte], ackRequest: AckRequest): Unit = {
        client.sendEvent("planets", SystemServlet.write("Sol").toString)
      }
    })
    socketio.start()

    def createPlanet(body: Body): TerrestrialBody = {
      val result = new TerrestrialBody(
        body.mass,
        new Vector2(body.orbit.radius.km,0),
        new Velocity(new Vector2(0, body.orbit.speed.ms)),
        body.radius,
        system)
      result.name = body.name
      result
    }

    val sun = system.add(new StellarBody(
      Constants.Sol.sol.mass,
      new Vector2(0.0,0.0),
      Constants.Sol.sol.radius,
      system))
    sun.name = "Sol"
    val luna = system.add(new TerrestrialBody(
      Constants.Sol.luna.mass,
      new Vector2(Constants.Sol.earth.orbit.radius.km - Constants.Sol.luna.orbit.radius.km, 0),
      new Velocity(new Vector2(0, 0 - (Constants.Sol.luna.orbit.speed.ms))),
      Constants.Sol.luna.radius,
      system
    ))
    luna.name = "Luna"
    Constants.Sol.bodies.foreach((body: Body) => {
      system.add(createPlanet(body))
    })

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
