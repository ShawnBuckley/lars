import lars.game.engine.celestial.body.MassiveBody
import lars.game.engine.math.{Physics, Vector2}
import org.testng.annotations.Test

object PhysicsTest {
  @Test
  def barycenter(): Boolean = {
    val m1 = new MassiveBody(1000, new Vector2(1,0))
    val m2 = new MassiveBody(1000, new Vector2(0,1))
    val barycenter = Physics.barycenter(m1, m2)
    barycenter == Vector2.identity
  }

  @Test
  def barycenterSeq(): Boolean = {
    val m1 = new MassiveBody(1000, new Vector2(1,0))
    val m2 = new MassiveBody(1000, new Vector2(0,1))
    val m3 = new MassiveBody(1000, new Vector2(-1,0))
    val m4 = new MassiveBody(1000, new Vector2(0,-1))
    val massives = Array(m1, m2, m3, m4)
    val barycenter = Physics.barycenter(massives)
    barycenter == Vector2.identity
  }
}
