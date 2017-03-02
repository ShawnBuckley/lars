package lars.core.physics.units

import lars.core.math.Vec2
import org.testng.Assert._
import org.testng.annotations.Test

class AngularMomentumTest {
  @Test
  def conservationAngularMomentum(): Unit = {
    val mass = new Mass(10)
    val vel = new Velocity(new Vec2(10,0))
    val rad = new Length(10)
    val newRad = new Length(5)

    val p = AngularMomentum(Momentum(mass, vel), rad)

    val result = p.conserve(newRad)
    assertEquals(result, AngularMomentum(Momentum(mass, Velocity(Vec2(20,0))), newRad))
  }
}
