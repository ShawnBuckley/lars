package lars

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class DebugServlet extends HttpServlet {
  override def service(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    Main.paused = !Main.paused
  }
}
