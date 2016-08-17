package lars.controllers

import javax.ws.rs.container.{ContainerRequestContext, ContainerResponseContext, ContainerResponseFilter}

class CORSResponseFilter extends ContainerResponseFilter {
  def filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext): Unit = {
    val headers = responseContext.getHeaders()
    headers.add("Access-Control-Allow-Origin", "*")
    headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
  }
}
