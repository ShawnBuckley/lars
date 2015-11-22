package lars

import lars.game.engine.Constants
import lars.game.engine.Constants.Body
import lars.game.engine.celestial.body.standard.{TerrestrialBody, StellarBody}
import lars.game.engine.celestial.container.System
import lars.game.engine.math.{Polar2, Vector2}
import lars.game.engine.physics.units.{Length, Velocity, Time}

import com.corundumstudio.socketio.listener._;
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, Configuration, SocketIOServer}
;

object Main {
  val system = new System(new Vector2(0,0), null)
  system.name = "Sol"

  var paused = false

  def main(args: Array[String]) {

    val server = new EmbeddedWebapp
    server.start()

    val config = new Configuration
    config.setHostname("localhost");
    config.setPort(9092);
    val socketio = new SocketIOServer(config)
    socketio.addEventListener[Array[Byte]]("planets", classOf[Array[Byte]], new DataListener[Array[Byte]] {
      override def onData(client: SocketIOClient, data: Array[Byte], ackRequest: AckRequest): Unit = {
        client.sendEvent("planets", PlanetServlet.write.toString)
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
    val mercury = system.add(createPlanet(Constants.Sol.mercury))
    val venus = system.add(createPlanet(Constants.Sol.venus))
    val earth = system.add(createPlanet(Constants.Sol.earth))
    val mars = system.add(createPlanet(Constants.Sol.mars))
    val jupiter = system.add(createPlanet(Constants.Sol.jupiter))
    val saturn = system.add(createPlanet(Constants.Sol.saturn))
    val uranus = system.add(createPlanet(Constants.Sol.uranus))
    val neptune = system.add(createPlanet(Constants.Sol.neptune))

    // min, max orbital lengths
    var min = Double.MaxValue
    var max = 0.0

    // state tracking
    var time = Time.second
    var count = 0

    while(true) {
      if(paused) {
        Thread.sleep(1000)
      } else {
        count += 1
        time += Time.second

        system.observe()
      }

      // update state


      // collect data
//      val polar = Polar2.convert(sun.location, earth.location)
//      val dist = new Length(polar.length).au // Constants.Sol.sol.radius.km
//      min = math.min(min, dist)
//      max = math.max(max, dist)

//      if(count % 1000000 == 0) {
//        //
//        println("Time: " + time.d + ", Angle: " + polar.angle + ", Dist: " + dist + ", min: " + min + ", max: " + max)
//      }
    }
  }
}
