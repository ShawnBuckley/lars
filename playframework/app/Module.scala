import java.nio.file.{Files, Paths}

import dao.CelestialDao
import dao.memory.MemoryCelestialDao
import lars.core.celestial.definition.Definition
import lars.core.observation.{Observer, StandardObserver}
import lars.core.physics.units.Time
import net.codingwell.scalaguice.ScalaModule
import util.JsonUtil

import scala.concurrent.ExecutionContext.Implicits.global

class
Module extends ScalaModule {
  override def configure(): Unit = {

    val celestialDao = new MemoryCelestialDao
    bind[CelestialDao].toInstance(celestialDao)

    val factory = new CelestialModelFactory(celestialDao)

    val galaxyData = JsonUtil.fromJson[Definition](new String(Files.readAllBytes(Paths.get("src/main/resources/milkyway.json"))))
    factory.create(galaxyData)

    val observer = new StandardObserver(1e7, Time.hour)
    bind[Observer].toInstance(observer)
  }

}
