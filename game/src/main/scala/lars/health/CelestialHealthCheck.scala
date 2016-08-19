package lars.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result

class CelestialHealthCheck extends HealthCheck {
  override def check(): Result = Result.healthy()
}
