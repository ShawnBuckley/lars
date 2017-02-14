package lars.core.physics

import lars.core.celestial.CelestialConstants
import lars.core.math.Vec2
import lars.core.physics.units._
import org.testng.annotations.Test
import org.testng.Assert._

class PhysicsTest {
  @Test
  def barycenter(): Unit = {
    val m1 = new MassiveBody(Mass(100000), Vec2(-5,0))
    val m2 = new MassiveBody(Mass(100000), Vec2( 5,0))
    assertEquals(Physics.barycenter(m1, m2), Barycenter(Mass(200000), Vec2.addIdent))

    val m3 = new MassiveBody(Mass(100000), Vec2( 0,0))
    val m4 = new MassiveBody(Mass(100),    Vec2(10,0))
    assertEquals(Physics.barycenter(m3, m4), Barycenter(Mass(100100), Vec2(0.00999000999000999, 0.0)))

    val m5 = new MassiveBody(Mass(100), Vec2(100,0))
    val m6 = new MassiveBody(Mass(100), Vec2(  0,0))
    assertEquals(Physics.barycenter(m5, m6), Barycenter(Mass(200), Vec2(50,0)))

    val m7 = new MassiveBody(Mass(100), Vec2(16,21))
    val m8 = new MassiveBody(Mass(100), Vec2(12,25))
    assertEquals(Physics.barycenter(m7, m8), Barycenter(Mass(200), Vec2(14,23)))
  }

  @Test
  def barycenterSeq(): Unit = {
    val m1 = new MassiveBody(Mass(5000),  Vec2(2,0))
    val m2 = new MassiveBody(Mass(5000),  Vec2(-2,0))
    val m3 = new MassiveBody(Mass(12000), Vec2(4,6))
    val m4 = new MassiveBody(Mass(8000),  Vec2(-2,-3))
    val massives = Array(m1, m2, m3, m4)
    assertEquals(Physics.barycenter(massives), Barycenter(Mass(30000), Vec2(1.0666666666666667, 1.6)))
  }

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

  @Test
  def barycenterRemove(): Unit = {
    val sun = new MassiveBody(CelestialConstants.Sol.sol.mass, Vec2(0,0))
    val earth = new MassiveBody(CelestialConstants.Sol.earth.mass, Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0))
    val barycenter = Physics.barycenter(sun, earth)
    assertEquals(Vec2(1.4959802299811888E8, 0), Physics.barycenterRemove(barycenter, sun).location)
    assertEquals(sun.location, Physics.barycenterRemove(barycenter, earth).location)
  }
}
