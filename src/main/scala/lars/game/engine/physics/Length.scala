package lars.game.engine.physics

case class Length(length: Long) {
  /**
    * Returns length in the standard unit of measure, km.
    * @return km
    */
  def km: Long =
    length
}
