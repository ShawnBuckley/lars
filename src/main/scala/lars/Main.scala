package lars

import lars.game.engine.Constants
import lars.game.engine.celestial.body.standard.{TerrestrialBody, StellarBody}
import lars.game.engine.celestial.container.System
import lars.game.engine.math.{Polar2, Vector2}
import lars.game.engine.physics.units.{Velocity, Time}

object Main {
  def main(args: Array[String]) {
    val system = new System(new Vector2(0,0), null)

    system.add(new StellarBody(
      Constants.Sol.sol.mass,
      new Vector2(0,0),
      Constants.Sol.sol.radius,
      system))
    val earth = system.add(new TerrestrialBody(
      Constants.Sol.earth.mass,
      new Vector2(Constants.Sol.earth.orbit.radius.km,0),
      new Velocity(new Vector2(0,Constants.Sol.earth.orbit.speed.ms)),
      Constants.Sol.earth.radius, system))

    // min, max orbital lengths
    var min = 1.0
    var max = 0.0

    // state tracking
    var time = Time.second
    var count = 0

    while(true) {
      // update state
      count += 1
      time += Time.second

      system.observe()

      // collect data
      val polar = Polar2.convert(earth.location)
      val percent = polar.length/Constants.Sol.earth.orbit.radius.km
      min = math.min(min, percent)
      max = math.max(max, percent)

      // print
      if(count % 1000000 == 0) {
        println("Time: " + time.d + ", Angle: " + polar.angle + ", Dist: " + percent + ", min: " + min + ", max: " + max)
      }
    }
  }
}
