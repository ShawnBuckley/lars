package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.System
import lars.core.math.Vec2
import lars.application.LARSApplication
import lars.core.celestial.CelestialConstants
import lars.core.physics.units.Time

object Main {
  var paused = false

  def main(args: Array[String]) {
    new LARSApplication().run("server", "jvm/src/main/resources/config.yml")

    val sol = new System(Some(CelestialConstants.Sol.name), new Vec2(0,0), Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.Sol.primaries, null, sol)
    Game.galaxy.add(sol)

    println("LARS Core started. Press enter to stop.")

    var runLoop = true

    new Thread(new Runnable {
      override def run(): Unit = {
        Console.in.readLine()
        println("LARS Core stopping.")
        runLoop = false
      }
    }).start()

    while(runLoop) {
      if(paused)
        Thread.sleep(1000)
      else
        Game.galaxy.observe(Time.minute)
    }

    println("LARS Core stopped.")
  }
}
