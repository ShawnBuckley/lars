package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.System
import lars.core.math.Vec2
import lars.application.LARSApplication
import lars.core.celestial.{CelestialConstants, TemporalMassive}
import lars.core.physics.Barycenter
import lars.core.physics.units.{Length, Time, Velocity}
import org.slf4j.{Logger, LoggerFactory}

object Main {
  var paused = false
  private var running = true

  val logger: Logger = LoggerFactory.getLogger("lars.Main")

  def createGalaxy(): Unit = {
    val sgrA = new System(Some(CelestialConstants.SagittariusA.name), Vec2.addIdent, Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.SagittariusA.A, sgrA)
    Game.galaxy.add(sgrA)

    val sol = new System(Some(CelestialConstants.Sol.name), new Vec2(2.349e17, 0), Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.Sol.Sol, sol)
    Game.galaxy.add(sol)

    val trappist1 = new System(Some(CelestialConstants.TRAPPIST1.name), new Vec2(2.349e17 + 39.5 * Length.Km.au, 0), Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.TRAPPIST1.A, trappist1)
    Game.galaxy.add(trappist1)

    // Calculate system speeds
    val galaxyBarycenter = Barycenter(Game.galaxy.mass, Game.galaxy.location)
    Game.galaxy.bodies.foreach({
      case body: TemporalMassive =>
        body.velocity = (galaxyBarycenter.remove(body).gravForce(body) / body.mass / Time.second).inverse
      case _ =>
    })
  }

  def start(): Unit = {
    logger.info("LARS started. Press enter to pause.")

    new Thread(() => {
      while(running) {
        Console.in.readLine()
        logger.info("LARS " + (if(paused) "unpaused." else "paused."))
        paused = !paused
      }
    }).start()

    while(running) {
      if(paused)
        Thread.sleep(1000)
      else
        Game.galaxy.observe(Time.minute)
    }

    logger.info("LARS stopped.")
  }

  def stop(): Unit = {
    running = false
  }

  def main(args: Array[String]) {
    new LARSApplication().run("server", "jvm/src/main/resources/config.yml")
    createGalaxy()
    start()
  }
}
