package lars.resource

import javax.ws.rs._
import javax.ws.rs.core.Response

import com.google.inject.Inject
import lars.core.celestial.container.Galaxy
import util.JsonUtil

@Path("/galaxy")
@Produces(Array[String]("application/json"))
class GalaxyResource @Inject()(galaxy: Galaxy) {
  @GET
  @Path("/")
  def get(): Response = {
    Response.ok(JsonUtil.toJson(galaxy)).build
  }
}