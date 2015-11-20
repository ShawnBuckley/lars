package lars

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import scalaxy.loops._
import org.json.{JSONObject, JSONArray}


class PlanetServlet extends HttpServlet {
  override def service(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val result = new JSONArray
    for(i <- 0 until Main.system.bodies.length optimized) {
      val body = Main.system.bodies(i)
      val planet = new JSONObject
      planet.put("x", body.location.x)
      planet.put("y", body.location.y)
      result.put(planet)
    }
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(result.toString)
  }
}
