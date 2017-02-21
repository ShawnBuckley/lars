package lars.core.physics.units

import lars.core.math.Vec2
import org.testng.Assert._
import org.testng.annotations.Test

class AngularMomentumTest {
  @Test
  def conservationAngularMomentum() = {
    val mass = new Mass(10)
    val vel = new Velocity(new Vec2(10,0))
    val rad = new Length(10)
    val newRad = new Length(5)
    val newVel = AngularMomentum.conserve(vel, rad, newRad)
    assertEquals(newVel, new Velocity(new Vec2(20,0)))
  }
}
