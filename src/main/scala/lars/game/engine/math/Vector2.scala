package lars.game.engine.math

case class Vector2(x: Long, y: Long) {
  def +(vec: Vector2) : Vector2 = new Vector2(x + vec.x, y + vec.y)
  def -(vec: Vector2) : Vector2 = new Vector2(x - vec.x, y + vec.y)
  def ==(vec: Vector2) : Boolean = (x == vec.x && y == vec.y)
}

object Vector2 {
  val identity = new Vector2(0,0)
}