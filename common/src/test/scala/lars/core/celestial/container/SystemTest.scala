package lars.core.celestial.container

import lars.core.ID
import lars.core.celestial.{Body, Constants}
import lars.core.physics.units.Time
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}
import org.scalatest.{BeforeAndAfter, FunSuite}

class SystemTest extends FunSuite with BeforeAndAfter {
  var galaxy: System = _
  var system: System = _

  val sol = Body(
    name = Some("Sol"),
    parent = None,
    lastObserved = Time.zero,
    mass = Constants.Sol.sol.mass,
    location = Vec2.addIdent,
    size = Some(Constants.Sol.sol.radius),
    orbiting = None,
    velocity = Some(Velocity.zero),
    surface = None)

  val earth = Body(
    name = Some("Earth"),
    parent = None,
    lastObserved = Time.zero,
    mass = Constants.Sol.earth.mass,
    location = Vec2.addIdent,
    size = Some(Constants.Sol.earth.radius),
    orbiting = None,
    velocity = Some(Velocity.zero),
    surface = None)

  before {
    galaxy = new System(ID(), Some("Milky Way"), Vec2.addIdent, Some(Velocity.zero), Time.zero, None)
    system = new System(ID(), Some("Sol"), Vec2.addIdent, Some(Velocity.zero), Time.zero, Some(galaxy))
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

  test("find earth in galaxy") {
    system.add(earth)
    galaxy.add(system)

    assert(galaxy.find("Earth").get == earth)
  }

  test("find none") {
    assert(system.find("Planet X").isEmpty)
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
    assert(earth.velocity.contains(oldVelocity.get - system.velocity.get))

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
    assert(earth.velocity.contains(oldVelocity.get + system.velocity.get))

    assert(sol.parent.get == system)

    assert(system.get(sol.name.get).get == sol)
    assert(system.get(earth.name.get).isEmpty)

    assert(galaxy.get(earth.name.get).get == earth)
    assert(galaxy.get(sol.name.get).isEmpty)
  }
}
