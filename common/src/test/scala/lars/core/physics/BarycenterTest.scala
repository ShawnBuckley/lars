package lars.core.physics

import lars.core.celestial.Constants
import lars.core.math.Vec2
import lars.core.physics.units.Mass
import org.testng.annotations.Test
import org.testng.Assert._

class BarycenterTest {

  @Test
  def addTest(): Unit = {
    val m1 = new MassiveBody(new Mass(100000), new Vec2(-5,0))
    val m2 = new MassiveBody(new Mass(100000), new Vec2( 5,0))

    val barycenter = Barycenter(m1.mass, m1.location)
    barycenter.add(m2)

    assertEquals(barycenter, Barycenter(new Mass(200000), Vec2.addIdent))
  }

  @Test
  def calculate(): Unit = {
    val m1 = new MassiveBody(Mass(100000), Vec2(-5,0))
    val m2 = new MassiveBody(Mass(100000), Vec2( 5,0))
    assertEquals(Barycenter.calculate(m1, m2), Barycenter(Mass(200000), Vec2.addIdent))

    val m3 = new MassiveBody(Mass(100000), Vec2( 0,0))
    val m4 = new MassiveBody(Mass(100),    Vec2(10,0))
    assertEquals(Barycenter.calculate(m3, m4), Barycenter(Mass(100100), Vec2(0.00999000999000999, 0.0)))

    val m5 = new MassiveBody(Mass(100), Vec2(100,0))
    val m6 = new MassiveBody(Mass(100), Vec2(  0,0))
    assertEquals(Barycenter.calculate(m5, m6), Barycenter(Mass(200), Vec2(50,0)))

    val m7 = new MassiveBody(Mass(100), Vec2(16,21))
    val m8 = new MassiveBody(Mass(100), Vec2(12,25))
    assertEquals(Barycenter.calculate(m7, m8), Barycenter(Mass(200), Vec2(14,23)))
  }

  @Test
  def calculateSeq(): Unit = {
    val m1 = new MassiveBody(Mass(5000),  Vec2(2,0))
    val m2 = new MassiveBody(Mass(5000),  Vec2(-2,0))
    val m3 = new MassiveBody(Mass(12000), Vec2(4,6))
    val m4 = new MassiveBody(Mass(8000),  Vec2(-2,-3))
    val massives = Array(m1, m2, m3, m4)
    val result = Barycenter(Mass(30000), Vec2(1.0666666666666667, 1.6))

    assertEquals(Barycenter.calculate(massives), result)

    val barycenter = Barycenter(Mass.zero, Vec2.addIdent)
    barycenter.add(massives)
    assertEquals(barycenter, result)
  }

  @Test
  def barycenterRemove(): Unit = {
    val sun = new MassiveBody(Constants.Sol.sol.mass, Vec2(0,0))
    val earth = new MassiveBody(Constants.Sol.earth.mass, Vec2(Constants.Sol.earth.orbit.radius.km,0))
    val barycenter = Barycenter.calculate(sun, earth)
    assertEquals(Vec2(1.4959802299811888E8, 0), Barycenter.remove(barycenter, sun).location)
    assertEquals(sun.location, Barycenter.remove(barycenter, earth).location)
  }
}
