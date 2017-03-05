package lars.core.celestial.body

import lars.core.celestial.{Constants, Sizeable}
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}
import org.scalatest.FunSuite

class SingularityTest extends FunSuite {
  test("collide") {
    val singularity = new Singularity(None, Constants.Sol.sol.mass, Vec2.addIdent, Velocity.zero, null)
    val other = new Sizeable {
      override def collide(other: Sizeable) = ???

      override var size: Length = Length(1)
      override var location: Vec2 = Vec2.addIdent
      override var mass: Mass = Mass(1000)
      override var velocity: Velocity = Velocity.zero
    }

    singularity.collide(other)
    assert(singularity.mass == Constants.Sol.sol.mass + Mass(1000))
  }

  test("schwarzschild radius") {
    val singularity = new Singularity(None, Constants.Sol.sol.mass, Vec2.addIdent, Velocity.zero, null)
    assert(singularity.schwarzschildRadius.km == 2.951555398799874)
  }

}
