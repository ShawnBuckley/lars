package lars.client.ui

import scala.collection.mutable

case class PlanetSprite(color: String, size: Int)

object PlanetSprite {
  val system = new mutable.HashMap[String, mutable.HashMap[String, PlanetSprite]]()

  val sol = new mutable.HashMap[String, PlanetSprite]()
  sol.put("Sol", PlanetSprite("yellow", 5))
  sol.put("Luna", PlanetSprite("white", 1))
  sol.put("Mercury", PlanetSprite("gray", 1))
  sol.put("Venus", PlanetSprite("red", 2))
  sol.put("Earth", PlanetSprite("blue", 2))
  sol.put("Mars", PlanetSprite("red", 1))
  sol.put("Ceres", PlanetSprite("white", 1))
  sol.put("Jupiter", PlanetSprite("brown", 4))
  sol.put("Saturn", PlanetSprite("orange", 4))
  sol.put("Uranus", PlanetSprite("white", 3))
  sol.put("Neptune", PlanetSprite("blue", 3))
  sol.put("Pluto", PlanetSprite("orange", 1))
  sol.put("Charon", PlanetSprite("white", 1))

  system.put("Sol", sol)
}