package lars.core

import com.fasterxml.jackson.annotation.JsonIgnore

import scala.annotation.meta.getter

trait Identity {
  @(JsonIgnore @getter)
  var id: Option[Long]
}
