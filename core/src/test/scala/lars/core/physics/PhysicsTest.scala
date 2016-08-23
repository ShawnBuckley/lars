package lars.core.physics

import lars.core.celestial.{CelestialConstants, Massive}
import lars.core.math.Vec2
import lars.core.physics.units._
import org.testng.annotations.Test
import org.testng.Assert._

class PhysicsTest {
  class MassiveBody(override var mass: Mass, override var location: Vec2) extends Massive {
    override var velocity: Velocity = _
  }

  @Test
  def barycenter() = {
    val m1 = new MassiveBody(new Mass(100000), new Vec2(-5,0))
    val m2 = new MassiveBody(new Mass(100000), new Vec2( 5,0))
    assertEquals(Physics.barycenter(m1, m2), new Barycenter(new Mass(200000), Vec2.addIdent))

    val m3 = new MassiveBody(new Mass(100000), new Vec2( 0,0))
    val m4 = new MassiveBody(new Mass(100),    new Vec2(10,0))
    assertEquals(Physics.barycenter(m3, m4), new Barycenter(new Mass(100100), new Vec2(0.00999000999000999, 0.0)))

    val m5 = new MassiveBody(new Mass(100), new Vec2(100,0))
    val m6 = new MassiveBody(new Mass(100), new Vec2(  0,0))
    assertEquals(Physics.barycenter(m5, m6), new Barycenter(new Mass(200), new Vec2(50,0)))

    val m7 = new MassiveBody(new Mass(100), new Vec2(16,21))
    val m8 = new MassiveBody(new Mass(100), new Vec2(12,25))
    assertEquals(Physics.barycenter(m7, m8), new Barycenter(new Mass(200), new Vec2(14,23)))
  }

  @Test
  def barycenterSeq() = {
    val m1 = new MassiveBody(new Mass(5000),  new Vec2(2,0))
    val m2 = new MassiveBody(new Mass(5000),  new Vec2(-2,0))
    val m3 = new MassiveBody(new Mass(12000), new Vec2(4,6))
    val m4 = new MassiveBody(new Mass(8000),  new Vec2(-2,-3))
    val massives = Array(m1, m2, m3, m4)
    assertEquals(Physics.barycenter(massives), new Barycenter(new Mass(30000), new Vec2(1.0666666666666667, 1.6)))
  }

  @Test
  def schwarzschildRadius() = {
    val radius = Physics.schwarzschildRadius(CelestialConstants.Sol.sol.mass)
    assertEquals(radius.km, 2.951555398799874)
  }

  @Test
  def gravForce() = {
    val sun = new MassiveBody(CelestialConstants.Sol.sol.mass, new Vec2(0,0))
    val earth = new MassiveBody(CelestialConstants.Sol.earth.mass, new Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0))
    assertEquals(Physics.gravForce(sun, earth).N.magnitude, 3.5395241695528224E22)
  }

  @Test
  def barycenterRemove() = {
    val sun = new MassiveBody(CelestialConstants.Sol.sol.mass, new Vec2(0,0))
    val earth = new MassiveBody(CelestialConstants.Sol.earth.mass, new Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0))
    val barycenter = Physics.barycenter(sun, earth)
    assertEquals(new Vec2(1.4959802299811888E8, 0), Physics.barycenterRemove(barycenter, sun).location)
    assertEquals(sun.location, Physics.barycenterRemove(barycenter, earth).location)
  }
}
