package lars.core.celestial

import lars.core.math.Vec2
import lars.core.physics.MassiveBody
import org.scalatest.FunSuite

class MassiveTest extends FunSuite {
  val sun = new MassiveBody(Constants.Sol.sol.mass, Vec2(0,0))
  val earth = new MassiveBody(Constants.Sol.earth.mass, Vec2(Constants.Sol.earth.orbit.radius.km,0))

  test("gravForce") {
    assert(sun.gravForce(earth).N.magnitude == 3.5395313764709807E22)
  }
}
