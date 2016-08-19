package lars.module

import com.codahale.metrics.health.HealthCheck
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import io.dropwizard.setup.Environment
import lars.application.LARSConfiguration
import lars.health.CelestialHealthCheck
import lars.resource.SystemResource

class CelestialModule(config: LARSConfiguration, env: Environment) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[LARSConfiguration]).toInstance(config)
    bind(classOf[Environment]).toInstance(env)
    val healthCheckBinder = Multibinder.newSetBinder(binder(), classOf[HealthCheck])
    healthCheckBinder.addBinding().to(classOf[CelestialHealthCheck])
    bind(classOf[SystemResource])
  }
}
