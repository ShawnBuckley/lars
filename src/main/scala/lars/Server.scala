package lars

import lars.controllers.PlanetController
import org.eclipse.jetty.server.{Server, ServerConnector}
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
  context.setParentLoaderPriority(true)
  context.addServlet(new ServletHolder(new DebugServlet), "/debug")
  server.setHandler(context)

  val jerseyServlet = context.addServlet(classOf[org.glassfish.jersey.servlet.ServletContainer], "/rest/*")
  jerseyServlet.setInitOrder(0)
  jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", classOf[PlanetController].getCanonicalName)

  def start() = server.start
  def stop() = server.stop
}