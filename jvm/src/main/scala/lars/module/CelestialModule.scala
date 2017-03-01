package lars.module

import com.codahale.metrics.health.HealthCheck
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import lars.core.celestial.container.Galaxy
import lars.health.CelestialHealthCheck
import lars.resource.SystemResource

class CelestialModule(galaxy: Galaxy) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Galaxy]).toInstance(galaxy)
    val healthCheckBinder = Multibinder.newSetBinder(binder(), classOf[HealthCheck])
    healthCheckBinder.addBinding().to(classOf[CelestialHealthCheck])
    bind(classOf[SystemResource])
  }
}
