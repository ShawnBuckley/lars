import lars.game.engine.celestial.body.MassiveBody
import lars.game.engine.math.{Physics, Vector2}
import org.testng.annotations.Test
import org.testng.Assert._

class PhysicsTest {
  @Test
  def barycenter() = {
    val m1 = new MassiveBody(100000,  new Vector2(-5,0))
    val m2 = new MassiveBody(100000,  new Vector2( 5,0))
    assertEquals(Physics.barycenter(m1, m2), Vector2.addIdent)

    val m3 = new MassiveBody(100000,  new Vector2( 0,0))
    val m4 = new MassiveBody(1000,    new Vector2(10,0))
    assertEquals(Physics.barycenter(m3, m4), Vector2.addIdent)

    val m5 = new MassiveBody(100, new Vector2(100,0))
    val m6 = new MassiveBody(100, new Vector2(  0,0))
    assertEquals(Physics.barycenter(m5, m6), new Vector2(50,0))

    val m7 = new MassiveBody(100, new Vector2(16, 21))
    val m8 = new MassiveBody(100, new Vector2(12, 25))
    assertEquals(Physics.barycenter(m7, m8), new Vector2(14,23))
  }

  @Test
  def barycenterSeq() = {
    val m1 = new MassiveBody(5000, new Vector2(2,0))
    val m2 = new MassiveBody(5000, new Vector2(-2,0))
    val m3 = new MassiveBody(12000, new Vector2(4,6))
    val m4 = new MassiveBody(8000, new Vector2(-2,-3))
    val massives = Array(m1, m2, m3, m4)
    assertEquals(Physics.barycenter(massives), new Vector2(1,1))
  }
}
