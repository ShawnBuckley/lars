package lars.controllers

import org.glassfish.jersey.server.ResourceConfig

class JaxRsApplication extends ResourceConfig {
  register(classOf[CORSResponseFilter])
}
