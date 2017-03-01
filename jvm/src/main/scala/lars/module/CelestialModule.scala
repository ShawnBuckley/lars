package lars.module

import com.codahale.metrics.health.HealthCheck
import lars.core.celestial.container.Galaxy
import lars.health.CelestialHealthCheck
import lars.resource.SystemResource
import net.codingwell.scalaguice.ScalaModule

class CelestialModule(galaxy: Galaxy) extends ScalaModule {
  override def configure(): Unit = {
    bind[Galaxy].toInstance(galaxy)
    bind[HealthCheck].to[CelestialHealthCheck]
    bind[SystemResource]
  }
}
