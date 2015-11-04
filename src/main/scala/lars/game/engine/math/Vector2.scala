package lars.game.engine.math

case class Vector2(x: Long, y: Long) {
  def +(vec: Vector2): Vector2 = new Vector2(x + vec.x, y + vec.y)
  def -(vec: Vector2): Vector2 = new Vector2(x - vec.x, y - vec.y)
  def *(factor: Int): Vector2 = new Vector2(x * factor, y * factor)
  def /(factor: Int): Vector2 = new Vector2(x / factor, y / factor)
  def ==(vec: Vector2) : Boolean = (x == vec.x && y == vec.y)
}

object Vector2 {
  /**
    * The additive identity.
    */
  val addIdent = new Vector2(0,0)

  /**
    * The multiplicative identity.
    */
  val mulIdent = new Vector2(1,1)

  /**
    * Returns the midpoint between two points.
    * @param one point1
    * @param two point2
    * @return midpoint
    */
  def midpoint(one: Vector2, two: Vector2): Vector2 = {
    new Vector2((one.x + two.x)/2, (one.y + two.y)/2)
  }

  /**
    * Returns the distance between two points.
    * @param one point1
    * @param two point2
    * @return distance
    */
  def distance(one: Vector2, two: Vector2): Long = {
    math.sqrt(math.pow(two.x - one.x, 2) + math.pow(two.y - two.x, 2)).toLong
  }
}