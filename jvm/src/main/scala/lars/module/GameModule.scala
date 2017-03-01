package lars.module

import com.google.inject.AbstractModule
import lars.resource.PauseResource

class GameModule() extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PauseResource])
  }
}
