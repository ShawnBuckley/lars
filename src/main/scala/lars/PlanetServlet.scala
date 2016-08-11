package lars

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import lars.game.engine.celestial.Massive
import org.json.{JSONArray, JSONObject}


class PlanetServlet extends HttpServlet {
  override def service(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(PlanetServlet.write.toString)
  }
}

object PlanetServlet {
  def write(): JSONArray = {
    val result = new JSONArray
    Main.system.bodies.foreach((body: Massive) => {
      val planet = new JSONObject
      if(body.name != null) planet.put("name", body.name)
      val location = new JSONObject
      location.put("x", body.location.x)
      location.put("y", body.location.y)
      planet.put("location", location)
      result.put(planet)
    })
    result
  }
}
