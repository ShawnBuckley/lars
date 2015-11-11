package lars.game.engine.math

import lars.game.engine.physics.units.{Volume, Length}
import org.testng.annotations.Test
import org.testng.Assert._

class SphereTest {
  @Test
  def volume() = {
    assertEquals(Sphere.volume(new Length(10)), new Volume(math.Pi * 1e3))
  }
}
