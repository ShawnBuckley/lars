package lars.resource

import javax.ws.rs._
import javax.ws.rs.core.Response

import com.google.inject.Inject
import lars.core.celestial.container.{Galaxy, System}
import lars.core.celestial.TemporalMassive
import util.JsonUtil

@Path("/system")
@Produces(Array[String]("application/json"))
class SystemResource @Inject()(galaxy: Galaxy) {
  @GET
  @Path("/{name}")
  def getPlanets(@PathParam("name") name: String): Response = {
    galaxy.get(name) match {
      case Some(system: System) => Response.ok(JsonUtil.toJson(system)).build
      case Some(body: TemporalMassive) => Response.ok(JsonUtil.toJson(body)).build
      case None => Response.status(Response.Status.NOT_FOUND).build
    }
  }
}