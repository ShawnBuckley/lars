package lars.game.engine.celestial.container

import lars.Game
import lars.game.engine.Constants
import lars.game.engine.celestial.body.standard.{TerrestrialBody, StellarBody}
import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.{Velocity, Mass}
import org.testng.annotations.Test
import org.testng.Assert._

class SystemTest {
  @Test
  def mass(): Unit = {
    val system = new System(Vector2.addIdent, Game.galaxy)
    val sun = system.add(new StellarBody(
      Constants.Sol.sol.mass,
      new Vector2(0,0),
      Constants.Sol.sol.radius,
      system))
    assertEquals(system.mass, sun.mass)
    val earth = system.add(new TerrestrialBody(
      Constants.Sol.earth.mass,
      new Vector2(Constants.Sol.earth.orbit.radius.km,0),
      new Velocity(new Vector2(0,Constants.Sol.earth.orbit.speed.ms)),
      Constants.Sol.earth.radius,
      system))
    assertEquals(system.mass, sun.mass + earth.mass)
    system.del(earth)
    assertEquals(system.mass, sun.mass)
    system.del(sun)
    assertEquals(system.mass, Mass.zero)
  }
}
