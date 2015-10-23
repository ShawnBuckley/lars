package lars

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class TestServlet extends HttpServlet {
  override def service(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    response.setContentType("text/plain")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write("something else!")
  }
}
