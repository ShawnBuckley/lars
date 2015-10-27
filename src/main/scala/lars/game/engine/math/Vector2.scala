package lars.game.engine.math

case class Vector2(x: Int, y: Int) {
  def +(vec: Vector2) : Vector2 = new Vector2(x + vec.x, y + vec.y)
  def -(vec: Vector2) : Vector2 = new Vector2(x - vec.x, y + vec.y)
  def ==(vec: Vector2) : Boolean = (x == vec.x && y == vec.y)
}
