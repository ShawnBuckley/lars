package lars.core.celestial.container

import lars.core.celestial.Constants
import lars.core.celestial.body.standard.{StellarBody, TerrestrialBody}
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}
import org.scalatest.FunSuite

class SystemTest extends FunSuite {
  test("mass") {
    val system = new System(Some("Sol"), Vec2.addIdent, Velocity.zero, null)

    val sun = new StellarBody(
      Some("Sol"),
      Constants.Sol.sol.mass,
      new Vec2(0,0),
      Velocity.zero,
      Constants.Sol.sol.radius,
      Some(system))

    val earth = new TerrestrialBody(
      Some("Earth"),
      Constants.Sol.earth.mass,
      new Vec2(Constants.Sol.earth.orbit.radius.km,0),
      Velocity.zero,
      Constants.Sol.earth.radius,
      Some(system))

    system.add(sun)
    assert(system.bodies.size == 1)
    assert(system.mass == sun.mass)

    system.add(earth)
    assert(system.bodies.size == 2)
    assert(system.mass == sun.mass + earth.mass)

    system.del(earth)

    assert(system.bodies.size == 1)
    assert(system.mass.kg == sun.mass.kg)

    system.del(sun)

    assert(system.bodies.isEmpty)
    assert(system.mass == Mass.zero)
  }
}
