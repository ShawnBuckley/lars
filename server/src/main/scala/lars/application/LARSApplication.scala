package lars.application

import javax.ws.rs.Path
import javax.ws.rs.container.{ContainerRequestFilter, ContainerResponseFilter}

import com.codahale.metrics.health.HealthCheck
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.google.inject.Module
import io.dropwizard.Application
import io.dropwizard.setup.Environment
import lars.application.GuiceInjector.{withInjector, wrap}
import lars.core.celestial.container.Galaxy
import lars.module.{CelestialModule, GameModule}

class LARSApplication(galaxy: Galaxy) extends Application[LARSConfiguration] {

  override def run(config: LARSConfiguration, env: Environment): Unit = {
    configure(config, env)
  }

  def configure(config: LARSConfiguration, env: Environment): Unit = {
    List(new CelestialModule(galaxy), new GameModule()).foreach((module: Module) => {
      withInjector(module) { injector =>
        injector.instancesWithAnnotation(classOf[Path]).foreach { r => env.jersey().register(r) }
        injector.instancesOfType(classOf[HealthCheck]).foreach { h => env.healthChecks.register(h.getClass.getName, h) }
        injector.instancesOfType(classOf[ContainerRequestFilter]).foreach { f => env.jersey().register(f) }
        injector.instancesOfType(classOf[ContainerResponseFilter]).foreach { f => env.jersey().register(f) }
      }
    })
    env.jersey.register(jacksonJaxbJsonProvider)
    env.jersey.setUrlPattern("/rest/*")
  }

  private def jacksonJaxbJsonProvider: JacksonJaxbJsonProvider = {
    val provider = new JacksonJaxbJsonProvider()
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(new DefaultScalaModule)
    objectMapper.registerModule(new JodaModule)
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    provider.setMapper(objectMapper)
    provider
  }
}