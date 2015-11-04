import lars.game.engine.celestial.body.MassiveBody
import lars.game.engine.math.{Physics, Vector2}
import org.testng.annotations.Test

object PhysicsTest {
  def createBody(factor: Int, loc: Vector2): MassiveBody = {
    new MassiveBody(1000 * factor, loc * factor)
  }

  @Test
  def barycenter(): Boolean = {
    val m1 = createBody(10, new Vector2(1,0))
    val m2 = createBody(1, new Vector2(0,1))
    Physics.barycenter(m1, m2) == Vector2.identity
  }

  @Test
  def barycenterSeq(): Boolean = {
    val m1 = createBody(5, new Vector2(1,0))
    val m2 = createBody(4, new Vector2(0,4))
    val m3 = createBody(8, new Vector2(-1,0))
    val m4 = createBody(3, new Vector2(0,-1))
    val massives = Array(m1, m2, m3, m4)
    Physics.barycenter(massives) == Vector2.identity
  }
}
