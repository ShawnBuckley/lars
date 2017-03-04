import java.nio.file.{Files, Paths}

import lars.core.celestial.body.standard.CelestialFactory
import lars.core.celestial.container.Galaxy
import lars.core.celestial.definition.Definition
import lars.core.observation.{Observer, StandardObserver}
import lars.core.physics.units.Time
import net.codingwell.scalaguice.ScalaModule
import util.JsonUtil

class Module extends ScalaModule {
  override def configure(): Unit = {
    val galaxyData = JsonUtil.fromJson[Definition](new String(Files.readAllBytes(Paths.get("src/main/resources/milkyway.json"))))
    val galaxy = new Galaxy(Some(galaxyData.name))
    CelestialFactory.createBodies(galaxyData, galaxy)

    val observer = new StandardObserver(1e7, Time.hour)

    bind[Galaxy].toInstance(galaxy)
    bind[Observer].toInstance(observer)
  }

}
