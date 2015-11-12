package lars.game.engine.physics.units

import org.testng.Assert._
import org.testng.annotations.Test

class AngularMomentumTest {
  @Test
  def conservationAngularMomentum() = {
    val mass = new Mass(10)
    val vel = new Speed(10)
    val rad = new Length(10)
    val newRad = new Length(5)
    val newVel = AngularMomentum.conserve(mass, vel, rad, newRad)
    assertEquals(newVel, new Speed(20))
  }
}
