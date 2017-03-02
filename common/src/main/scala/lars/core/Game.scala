package lars.core

import lars.core.celestial.container.Galaxy
import lars.core.physics.units.Time

class Game(val galaxy: Galaxy) {
  private var date = Time.zero

  private var paused = true
  private var running = true

  def pause(): Unit = {
    paused = !paused
  }

  def start(): Unit = {
    def getPaused = if(paused) "unpause" else "pause"

    println("LARS started. Press enter to " + getPaused + ".")

    new Thread(() => {
      while(running) {
        Console.in.readLine()
        println("LARS " + getPaused + "d.")
        paused = !paused
      }
    }).start()

    while(running) {
      if(paused)
        Thread.sleep(1000)
      else
        galaxy.observe(Time.minute)
    }

    println("LARS stopped.")
  }

  def stop(): Unit = {
    running = false
  }
}
