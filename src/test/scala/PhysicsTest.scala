import lars.game.engine.celestial.body.MassiveBody
import lars.game.engine.math.{Physics, Vector2}
import org.testng.annotations.Test

object PhysicsTest {
  @Test
  def barycenter(): Boolean = {
    val m1 = new MassiveBody(10000, new Vector2(10,0))
    val m2 = new MassiveBody(1000, new Vector2(0,1))
    Physics.barycenter(m1, m2) == Vector2.identity
  }

  @Test
  def barycenterSeq(): Boolean = {
    val m1 = new MassiveBody(5000, new Vector2(5,0))
    val m2 = new MassiveBody(4000, new Vector2(0,4))
    val m3 = new MassiveBody(8000, new Vector2(-8,0))
    val m4 = new MassiveBody(3000, new Vector2(0,-3))
    val massives = Array(m1, m2, m3, m4)
    Physics.barycenter(massives) == Vector2.identity
  }
}
