package lars

import lars.core.celestial.body.standard._
import lars.core.celestial.container.System
import lars.core.math.Vec2
import lars.application.LARSApplication
import lars.core.celestial.{CelestialConstants, TemporalMassive}
import lars.core.physics.Barycenter
import lars.core.physics.units.{Length, Time, Velocity}

object Main {
  var paused = false

  def main(args: Array[String]) {
    new LARSApplication().run("server", "jvm/src/main/resources/config.yml")

    val sgrA = new System(Some(CelestialConstants.SagittariusA.name), Vec2.addIdent, Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.SagittariusA.A, sgrA)
    Game.galaxy.add(sgrA)

    val sol = new System(Some(CelestialConstants.Sol.name), new Vec2(2.349e17, 0), Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.Sol.Sol, sol)
    Game.galaxy.add(sol)

    val trappist1 = new System(Some(CelestialConstants.TRAPPIST1.name), new Vec2(2.349e17 + 39.5 * Length.Km.au, 0), Velocity.zero, Some(Game.galaxy))
    CelestialFactory.createBodies(CelestialConstants.TRAPPIST1.A, trappist1)
    Game.galaxy.add(trappist1)

    // Calculate system speeds
    val galaxyBarycenter = Barycenter(Game.galaxy.mass, Game.galaxy.location)
    Game.galaxy.getAll.foreach({
      case _ =>
      case body: TemporalMassive =>
        val velocity = Barycenter.remove(galaxyBarycenter, body).gravForce(body) / body.mass / Time.second
        body.velocity = Velocity(Vec2(velocity.ms.y, velocity.ms.x))
    })

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
