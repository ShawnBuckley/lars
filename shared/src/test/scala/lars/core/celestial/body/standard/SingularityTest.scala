package lars.core.celestial.body.standard

import lars.core.celestial.{Constants, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}
import org.testng.annotations.Test
import org.testng.Assert._

class SingularityTest {
  @Test
  def collide(): Unit = {
    val singularity = new Singularity(None, Constants.Sol.sol.mass, Vec2.addIdent, Velocity.zero, null)
    val other = new Sizeable {
      override def collide(other: Sizeable) = ???

      override var size: Length = Length(1)
      override var location: Vec2 = Vec2.addIdent
      override var mass: Mass = Mass(1000)
      override var velocity: Velocity = Velocity.zero
    }

    singularity.collide(other)
    assertEquals(singularity.mass, Constants.Sol.sol.mass + Mass(1000))
  }

  @Test
  def schwarzschildRadius(): Unit = {
    val singularity = new Singularity(None, Constants.Sol.sol.mass, Vec2.addIdent, Velocity.zero, null)
    assertEquals(singularity.schwarzschildRadius.km, 2.951555398799874)
  }

}
