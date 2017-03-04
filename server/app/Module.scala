import java.nio.file.{Files, Paths}

import lars.core.Game
import lars.core.celestial.body.standard.CelestialFactory
import lars.core.celestial.container.Galaxy
import lars.core.celestial.definition.Definition
import lars.core.observation.StandardObserver
import lars.core.physics.units.Time
import net.codingwell.scalaguice.ScalaModule
import util.JsonUtil

class Module extends ScalaModule {
  override def configure(): Unit = {
    def createGalaxy(filename: String): Galaxy = {
      val galaxyData = JsonUtil.fromJson[Definition](new String(Files.readAllBytes(Paths.get(filename))))
      val galaxy = new Galaxy(Some(galaxyData.name))
      CelestialFactory.createBodies(galaxyData, galaxy)
      galaxy
    }

    val game = new Game(createGalaxy("src/main/resources/milkyway.json"),
      new StandardObserver(System.currentTimeMillis(), 1e7, Time.hour))

    bind[Game].toInstance(game)
  }

}
