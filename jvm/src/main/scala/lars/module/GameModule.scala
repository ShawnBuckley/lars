package lars.module

import javax.inject.Named

import com.google.inject.AbstractModule
import io.dropwizard.setup.Environment
import lars.application.LARSConfiguration
import lars.resource.PauseResource

class GameModule(config: LARSConfiguration, env: Environment) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[LARSConfiguration]).toInstance(config)
    bind(classOf[Environment]).toInstance(env)
    bind(classOf[PauseResource])
  }
}
