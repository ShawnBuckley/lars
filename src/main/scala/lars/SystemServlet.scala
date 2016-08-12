package lars

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import lars.game.engine.celestial.Massive
import org.json.{JSONArray, JSONObject}

/**
  * Created by shawn on 8/11/16.
  */
class SystemServlet extends HttpServlet {
  override def service(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val name = request.getRequestURI.split("/")
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(SystemServlet.write(name(name.length-1)))
  }
}

object SystemServlet {
  def write(name: String): String = {
    val result = new JSONArray
    Game.galaxy.getSystem(name).bodies.foreach((body: Massive) => {
      val planet = new JSONObject
      if(body.name != null) planet.put("name", body.name)
      val location = new JSONObject
      location.put("x", body.location.x)
      location.put("y", body.location.y)
      planet.put("location", location)
      result.put(planet)
    })
    result.toString
  }
}