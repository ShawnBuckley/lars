package lars.module

import lars.core.Game
import lars.resource.GameResource
import net.codingwell.scalaguice.ScalaModule

class GameModule(game: Game) extends ScalaModule {
  override def configure(): Unit = {
    bind[Game].toInstance(game)
    bind[GameResource]
  }
}
