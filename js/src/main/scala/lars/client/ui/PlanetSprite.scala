package lars.client.ui

import scala.collection.mutable

object PlanetSprite {
  val system = new mutable.HashMap[String, mutable.HashMap[String, String]]()

  val sol = new mutable.HashMap[String, String]()
  sol.put("Sol", "yellow")
  sol.put("Luna", "white")
  sol.put("Mercury", "gray")
  sol.put("Venus", "red")
  sol.put("Earth", "blue")
  sol.put("Mars", "red")
  sol.put("Ceres", "white")
  sol.put("Jupiter", "brown")
  sol.put("Saturn", "orange")
  sol.put("Uranus", "white")
  sol.put("Neptune", "blue")
  sol.put("Pluto", "orange")
  sol.put("Charon", "white")

  system.put("Sol", sol)

  val trappist1 = new mutable.HashMap[String, String]()
  trappist1.put("a", "red")
  trappist1.put("b", "orange")
  trappist1.put("c", "yellow")
  trappist1.put("d", "blue")
  trappist1.put("e", "blue")
  trappist1.put("f", "blue")
  trappist1.put("g", "green")
  trappist1.put("h", "white")

  system.put("TRAPPIST-1", trappist1)
}