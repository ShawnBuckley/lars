package lars.core.celestial.container

import lars.core.celestial.CelestialConstants
import lars.core.celestial.body.standard.{StellarBody, TerrestrialBody}
import lars.core.math.Vec2
import lars.core.physics.units.{Mass, Velocity}
import org.testng.annotations.Test
import org.testng.Assert._

class SystemTest {
  @Test
  def mass(): Unit = {
    val system = new System(Some("Sol"), Vec2.addIdent, null)

    val sun = new StellarBody(
      Some("Sol"),
      CelestialConstants.Sol.sol.mass,
      new Vec2(0,0),
      Velocity.zero,
      CelestialConstants.Sol.sol.radius,
      Some(system))

    val earth = new TerrestrialBody(
      Some("Earth"),
      CelestialConstants.Sol.earth.mass,
      new Vec2(CelestialConstants.Sol.earth.orbit.radius.km,0),
      new Velocity(new Vec2(0,CelestialConstants.Sol.earth.orbit.speed.ms)),
      CelestialConstants.Sol.earth.radius,
      Some(system))

    system.add(sun)
    assertEquals(system.getAll.size, 1)
    assertEquals(system.mass, sun.mass)

    system.add(earth)
    assertEquals(system.getAll.size, 2)
    assertEquals(system.mass, sun.mass + earth.mass)

    system.del(earth)

    assertEquals(system.getAll.size, 1)
    assertEquals(system.mass.kg, sun.mass.kg)

    system.del(sun)

    assertEquals(system.getAll.size, 0)
    assertEquals(system.mass, Mass.zero)
  }
}
