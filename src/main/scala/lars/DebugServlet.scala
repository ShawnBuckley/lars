package lars

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

import org.json.JSONObject

class DebugServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val status = new JSONObject
    status.put("paused", Main.paused)

    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(status.toString)
  }

  override def doPost(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    Main.paused = !Main.paused
  }
}
