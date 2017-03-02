package lars.module

import com.codahale.metrics.health.HealthCheck
import lars.core.Game
import lars.health.CelestialHealthCheck
import lars.resource.{GalaxyResource, SystemResource}
import net.codingwell.scalaguice.ScalaModule

class CelestialModule(game: Game) extends ScalaModule {
  override def configure(): Unit = {
    bind[Game].toInstance(game)
    bind[HealthCheck].to[CelestialHealthCheck]
    bind[GalaxyResource]
    bind[SystemResource]
  }
}
