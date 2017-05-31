import dao.CelestialDao
import dao.slick.SlickCelestialDao
import lars.core.observation.{Observer, StandardObserver}
import lars.core.physics.units.Time
import net.codingwell.scalaguice.ScalaModule

class Module extends ScalaModule {
  override def configure(): Unit = {
    bind[CelestialDao].to[SlickCelestialDao]
    bind[Observer].toInstance(new StandardObserver(1e7, Time.hour))
  }
}
