package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.Galaxy
import lars.application.LARSApplication
import lars.core.physics.units.Time
import org.slf4j.{Logger, LoggerFactory}
import java.nio.file.Files
import java.nio.file.Paths

import lars.core.celestial.definition.Definition
import util.JsonUtil


object Main {
  var paused = true
  private var running = true

  val logger: Logger = LoggerFactory.getLogger("lars.Main")

  def createGalaxy(): Unit = {
    val galString = new String(Files.readAllBytes(Paths.get("jvm/src/main/resources/milkyway.json")))
    val galaxyData = JsonUtil.fromJson[Definition](galString)

    Game.galaxy = new Galaxy(Some(galaxyData.name))
    CelestialFactory.createBodies(galaxyData, Game.galaxy)
  }

  def start(): Unit = {
    def getPaused = if(paused) "unpaused." else "paused."

    logger.info("LARS started. Press enter to " + getPaused)

    new Thread(() => {
      while(running) {
        Console.in.readLine()
        logger.info("LARS " + getPaused)
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
