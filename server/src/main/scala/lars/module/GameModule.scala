package lars.module

import lars.resource.PauseResource
import net.codingwell.scalaguice.ScalaModule

class GameModule() extends ScalaModule {
  override def configure(): Unit = {
    bind[PauseResource]
  }
}
