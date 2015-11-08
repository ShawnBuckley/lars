package lars.game.engine.physics

import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Mass
import org.testng.annotations.Test
import org.testng.Assert._

class PhysicsTest {
  class MassiveBody(_mass: Mass, _loc: Vector2) extends Massive {
    override def mass: Mass = _mass
    override def location: Vector2 = _loc

    override def location_=(loc: Vector2): Unit = ???
    override def observe(): Unit = ???
    override def drift_=(vec: Vector2): Unit = ???
    override def drift: Vector2 = ???
  }

  @Test
  def barycenter() = {
    val m1 = new MassiveBody(new Mass(100000), new Vector2(-5,0))
    val m2 = new MassiveBody(new Mass(100000), new Vector2( 5,0))
    assertEquals(Physics.barycenter(m1, m2), Vector2.addIdent)

    val m3 = new MassiveBody(new Mass(100000), new Vector2( 0,0))
    val m4 = new MassiveBody(new Mass(100),    new Vector2(10,0))
    assertEquals(Physics.barycenter(m3, m4).round, Vector2.addIdent)

    val m5 = new MassiveBody(new Mass(100), new Vector2(100,0))
    val m6 = new MassiveBody(new Mass(100), new Vector2(  0,0))
    assertEquals(Physics.barycenter(m5, m6), new Vector2(50,0))

    val m7 = new MassiveBody(new Mass(100), new Vector2(16,21))
    val m8 = new MassiveBody(new Mass(100), new Vector2(12,25))
    assertEquals(Physics.barycenter(m7, m8), new Vector2(14,23))
  }

  @Test
  def barycenterSeq() = {
    val m1 = new MassiveBody(new Mass(5000),  new Vector2(2,0))
    val m2 = new MassiveBody(new Mass(5000),  new Vector2(-2,0))
    val m3 = new MassiveBody(new Mass(12000), new Vector2(4,6))
    val m4 = new MassiveBody(new Mass(8000),  new Vector2(-2,-3))
    val massives = Array(m1, m2, m3, m4)
    assertEquals(Physics.barycenter(massives).round, new Vector2(1,1))
  }

  @Test
  def schwarzschildRadius() = {
    val radius = Physics.schwarzschildRadius(new Mass(10000000000000000000000.0))
    assertEquals(math.round(radius.km), 4450)
  }
}
