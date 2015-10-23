package lars;

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext

class EmbeddedWebapp(val port: Int = 8080, val contextPath: String = "/") {
  val server = new Server()
  val connector = new ServerConnector(server)
  connector.setPort(port)
  server.addConnector(connector)

  val context = new WebAppContext()
  context.setContextPath(contextPath)
  context.setWar("src/main/webapp")
  context.addServlet(new ServletHolder(new TestServlet), "/test")
  server.setHandler(context)

  def start = server.start
  def stop = server.stop
}