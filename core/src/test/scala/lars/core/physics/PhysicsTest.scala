package lars.core.physics

import lars.core.celestial.CelestialConstants
import lars.core.math.Vec2
import org.testng.annotations.Test
import org.testng.Assert._

class PhysicsTest {
  @Test
  def schwarzschildRadius(): Unit = {
    val radius = Physics.schwarzschildRadius(CelestialConstants.Sol.sol.mass)
    assertEquals(radius.km, 2.951555398799874)
  }

  @Test
  def gravForce(): Unit = {
    val sun = new MassiveBody(CelestialConstants.Sol.sol.mass, Vec2(0,0))
    val earth = new MassiveBody(CelestialConstants.Sol.earth.mass, Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0))
    assertEquals(Physics.gravForce(sun, earth).N.magnitude, 3.539524169552822E22)
  }
}
