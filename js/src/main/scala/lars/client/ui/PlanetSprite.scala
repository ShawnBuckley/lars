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
}