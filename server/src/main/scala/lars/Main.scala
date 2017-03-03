package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.Galaxy
import lars.application.LARSApplication
import org.slf4j.{Logger, LoggerFactory}
import java.nio.file.Files
import java.nio.file.Paths

import lars.core.Game
import lars.core.celestial.definition.Definition
import lars.core.observation.StandardObserver
import lars.core.physics.units.Time
import util.JsonUtil

object Main {
  val logger: Logger = LoggerFactory.getLogger("lars.Main")

  private val game = new Game(createGalaxy("server/src/main/resources/milkyway.json"),
    new StandardObserver(System.currentTimeMillis(), 1e7, Time.hour))

  def createGalaxy(filename: String): Galaxy = {
    val galaxyData = JsonUtil.fromJson[Definition](new String(Files.readAllBytes(Paths.get(filename))))
    val galaxy = new Galaxy(Some(galaxyData.name))
    CelestialFactory.createBodies(galaxyData, galaxy)
    galaxy
  }

  def main(args: Array[String]) {
    new LARSApplication(game).run("server", "server/src/main/resources/config.yml")
  }
}
