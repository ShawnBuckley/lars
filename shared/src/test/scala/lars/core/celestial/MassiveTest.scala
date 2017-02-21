package lars.core.celestial

import lars.core.math.Vec2
import lars.core.physics.MassiveBody
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class MassiveTest {
  @Test
  def gravForce(): Unit = {
    val sun = new MassiveBody(CelestialConstants.Sol.sol.mass, Vec2(0,0))
    val earth = new MassiveBody(CelestialConstants.Sol.earth.mass, Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0))
    assertEquals(sun.gravForce(earth).N.magnitude, 3.539524169552822E22)
  }
}
