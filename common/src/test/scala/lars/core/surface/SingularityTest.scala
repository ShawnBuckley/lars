package lars.core.surface

import lars.core.celestial.{Body, Constants}
import lars.core.math.Vec2
import lars.core.physics.units.{Time, Velocity}
import org.scalatest.{BeforeAndAfter, FunSuite}

class SingularityTest extends FunSuite with BeforeAndAfter {

  var singularity: Body = _
  var singularitySurface: Singularity = _
  var earth: Body = _

  before {
    singularity = Body(
      name = Some("Sol"),
      parent = None,
      lastObserved = Time.zero,
      mass = Constants.Sol.sol.mass,
      location = Vec2.addIdent,
      size = Some(Singularity.schwarzschildRadius(Constants.Sol.sol.mass)),
      orbiting = None,
      velocity = Some(Velocity.zero),
      surface = None)

    singularitySurface = new Singularity(singularity)

    earth = Body(
      name = Some("Earth"),
      parent = None,
      lastObserved = Time.zero,
      mass = Constants.Sol.earth.mass,
      location = Vec2.addIdent,
      size = Some(Constants.Sol.earth.radius),
      orbiting = None,
      velocity = Some(Velocity.zero),
      surface = None)
  }

  test("collide") {
    singularitySurface.collide(earth)
    assert(singularity.mass == Constants.Sol.sol.mass + Constants.Sol.earth.mass)
  }

  test("schwarzschild radius") {
    assert(singularity.size.get.km == 2.951555398799874)
  }

}
