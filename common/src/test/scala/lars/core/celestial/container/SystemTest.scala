package lars.core.celestial.container

import lars.core.celestial.Constants
import lars.core.celestial.body.standard.{StellarBody, TerrestrialBody}
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}
import org.scalatest.{BeforeAndAfter, FunSuite}

class SystemTest extends FunSuite with BeforeAndAfter {
  var galaxy: System = _
  var system: System = _

  val sol = new StellarBody(
    Some("Sol"),
    Constants.Sol.sol.mass,
    new Vec2(0,0),
    Velocity.zero,
    Constants.Sol.sol.radius,
    None)

  val earth = new TerrestrialBody(
    Some("Earth"),
    Constants.Sol.earth.mass,
    new Vec2(Constants.Sol.earth.orbit.radius.km,0),
    Velocity.zero,
    Constants.Sol.earth.radius,
    None)

  before {
    galaxy = new System(Some("Milky Way"), Vec2.addIdent, Velocity.zero, None)
    system = new System(Some("Sol"), Vec2.addIdent, Velocity.zero, Some(galaxy))
  }

  test("add body") {
    system.add(sol)
    assert(system.bodies.size == 1)
    assert(system.mass == sol.mass)

    system.add(earth)
    assert(system.bodies.size == 2)
    assert(system.mass == sol.mass + earth.mass)
  }

  test("remove body") {
    system.add(sol)
    system.add(earth)
    assert(system.bodies.size == 2)
    assert(system.mass == sol.mass + earth.mass)

    assert(system.del(earth))
    assert(system.bodies.size == 1)
    assert(system.mass.kg == sol.mass.kg)

    assert(system.del(sol))
    assert(system.bodies.isEmpty)
    assert(system.mass == Mass.zero)
  }

  test("remove bogus body") {
    system.add(sol)
    assert(!system.del(earth))
    assert(system.bodies.length == 1)
    assert(system.mass == sol.mass)
  }

  test("get body") {
    system.add(sol)
    system.add(earth)

    assert(system.get("Sol").get == sol)
  }

  test("get none") {
    system.add(sol)
    system.add(earth)

    assert(system.get("Planet X").isEmpty)
  }

  test("enter") {
    galaxy.add(sol)
    galaxy.add(earth)

    sol.parent = Some(galaxy)
    earth.parent = Some(galaxy)

    val oldLocation = earth.location
    val oldVelocity = earth.velocity
    system.enter(earth)

    assert(earth.parent.get == system)
    assert(earth.location == oldLocation - system.location)
    assert(earth.velocity == oldVelocity - system.velocity)

    assert(sol.parent.get == galaxy)

    assert(system.get(earth.name.get).get == earth)
    assert(system.get(sol.name.get).isEmpty)

    assert(galaxy.get(earth.name.get).isEmpty)
    assert(galaxy.get(sol.name.get).get == sol)
  }

  test("escape") {
    system.add(sol)
    system.add(earth)

    sol.parent = Some(system)
    earth.parent = Some(system)

    val oldLocation = earth.location
    val oldVelocity = earth.velocity
    system.escape(earth)

    assert(earth.parent.get == galaxy)
    assert(earth.location == oldLocation + system.location)
    assert(earth.velocity == oldVelocity + system.velocity)

    assert(sol.parent.get == system)

    assert(system.get(sol.name.get).get == sol)
    assert(system.get(earth.name.get).isEmpty)

    assert(galaxy.get(earth.name.get).get == earth)
    assert(galaxy.get(sol.name.get).isEmpty)
  }
}
