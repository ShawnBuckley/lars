package lars.core

import java.util.UUID

import com.fasterxml.jackson.annotation.JsonIgnore

import scala.annotation.meta.getter

trait Identity {
  @(JsonIgnore @getter)
  var id: ID
}

case class ID(private var uuid: UUID = null) {
  def get: UUID = {
    if(uuid == null) uuid = UUID.randomUUID
    uuid
  }
}