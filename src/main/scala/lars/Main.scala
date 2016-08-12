package lars

import lars.game.engine.celestial.body.standard._
import lars.game.engine.celestial.container.System
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Velocity
import com.corundumstudio.socketio.listener._
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import lars.game.engine.Constants
import lars.game.engine.celestial.Massive
import lars.game.engine.celestial.body.BodyDefinition

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
        client.sendEvent("planets", SystemServlet.write("Sol").toString)
      }
    })
    socketio.start()

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Create system
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val system = new System(new Vector2(0,0), null)
    system.name = Constants.Sol.name

    Game.galaxy.addSystem(system)

    def createBody(body: BodyDefinition, primary: Vector2): Massive = {
      body.classification match {
        case "singularity" => {
          val result = new Singularity(
            body.mass,
            primary + new Vector2(body.orbit.radius.km,0),
            new Velocity(new Vector2(0, body.orbit.speed.ms)),
            system)
          result.name = body.name
          result
        }
        case "stellar" => {
          val result = new StellarBody(
            body.mass,
            primary + new Vector2(body.orbit.radius.km,0),
            new Velocity(new Vector2(0, body.orbit.speed.ms)),
            body.radius,
            system)
          result.name = body.name
          result
        }
        case "gaseous" => {
          val result = new GaseousBody(
            body.mass,
            primary + new Vector2(body.orbit.radius.km,0),
            new Velocity(new Vector2(0, body.orbit.speed.ms)),
            body.radius,
            system)
          result.name = body.name
          result
        }
        case "terrestrial" => {
          val result = new TerrestrialBody(
            body.mass,
            primary + new Vector2(body.orbit.radius.km,0),
            new Velocity(new Vector2(0, body.orbit.speed.ms)),
            body.radius,
            system)
          result.name = body.name
          result
        }
        case "micro" => {
          val result = new MicroBody(
            body.mass,
            primary + new Vector2(body.orbit.radius.km,0),
            new Velocity(new Vector2(0, body.orbit.speed.ms)),
            body.radius,
            system)
          result.name = body.name
          result
        }
        case default => null
      }
    }

    def createBodies(bodies: List[BodyDefinition], primary: Vector2): Unit = {
      bodies.foreach((body: BodyDefinition) => {
        println("creating " + body.name)
        system.add(createBody(body, primary))
        if(body.satellites != null && body.satellites.nonEmpty) createBodies(body.satellites, new Vector2(body.orbit.radius.km, 0))
      })
    }
    createBodies(Constants.Sol.primaries, new Vector2(0,0))

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
