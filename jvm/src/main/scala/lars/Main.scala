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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Create system
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val sol = new System(Some(CelestialConstants.Sol.name), new Vec2(0,0), null)
    Game.galaxy.addSystem(sol)
    CelestialFactory.createBodies(CelestialConstants.Sol.primaries, null, sol)

    val xygon = new System(Some(CelestialConstants.Xygon.name), Vec2.addIdent, null)
    Game.galaxy.addSystem(xygon)
    CelestialFactory.createBodies(CelestialConstants.Xygon.primaries, null, xygon)


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Main loop
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
//      Thread.sleep(1000)
//      if(!paused) system.observe()
      if(paused) Thread.sleep(1000)
      else {
        sol.observe(Time.minute)
//        xygon.observe(Time.second)
      }
    }

    println("LARS Core stopped.")
  }
}
