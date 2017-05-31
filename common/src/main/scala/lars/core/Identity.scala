package lars.core

import java.util.UUID

trait Identity {
  var id: ID
}

case class ID(private var uuid: UUID = null) {
  def get: UUID = {
    if(uuid == null) uuid = UUID.randomUUID
    uuid
  }
}