package lars

import lars.game.engine.Constants
import lars.game.engine.celestial.body.standard.{TerrestrialBody, StellarBody}
import lars.game.engine.math.{Polar2, Vector2}
import lars.game.engine.physics.Physics
import lars.game.engine.physics.units.{Length, AngularMomentum, Velocity, Time}

object Main {
  def main(args: Array[String]) {
    // Simulation
    val sec = Time.in.s(1)
    val sun = new StellarBody(Constants.Sol.sol.mass, new Vector2(0,0), Constants.Sol.sol.radius, null)
    val earth = new TerrestrialBody(Constants.Sol.earth.mass, new Vector2(Constants.Sol.earth.orbit.radius.km,0), Constants.Sol.earth.radius, null)
    earth.velocity = new Velocity(new Vector2(0,Constants.Sol.earth.orbit.speed.ms))

    // min, max orbital lengths
    var min = 1.0
    var max = 0.0

    // state tracking
    var time = sec
    var count = 0

    while(true) {
      // update state
      count += 1
      time += sec

      // track last position - for angular momentum conservation
      val radius = new Length(earth.location.length)
      val lastLocation = earth.location

      // update earth
      earth.velocity += Physics.gravForce(sun, earth) / earth.mass / sec
      earth.location += earth.velocity.kms
      earth.velocity = AngularMomentum.conserve(earth.mass, earth.velocity, new Length(lastLocation.length), radius)

      // collect data
      val polar = Polar2.convert(earth.location)
      val percent = polar.length/Constants.Sol.earth.orbit.radius.km
      min = math.min(min, percent)
      max = math.max(max, percent)

      // print
      if(math.round(time.w) % 2 == 0) {
        println("Time: " + time.d + ", Angle: " + polar.angle + ", Dist: " + percent + ", min: " + min + ", max: " + max)
      }
    }
  }
}
