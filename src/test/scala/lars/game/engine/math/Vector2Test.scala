package lars.game.engine.math

import org.testng.annotations.Test
import org.testng.Assert._

class Vector2Test {
  val x1 = 14
  val y2 = 6

  val y1 = 5
  val x2 = 3

  val scalar = 2

  // First point, larger of the two
  val larger = new Vector2(x1, y1)

  // Second point, smaller of the two
  val smaller = new Vector2(x2, y2)

  @Test
  def +() =
    assertEquals(larger + smaller, new Vector2(x1 + x2, y1 + y2))

  @Test
  def -() =
    assertEquals(larger - smaller, new Vector2(x1 - x2, y1 - y2))

  @Test
  def *() =
    assertEquals(larger * scalar, new Vector2(x1 * scalar, y1 * scalar))

  @Test
  def /() =
    assertEquals(larger / scalar, new Vector2(x1 / scalar, y1 / scalar))

  @Test
  def >() =
    assert(larger > smaller)

  @Test
  def <() =
    assert(smaller < larger)

  @Test
  def >=() = {
    assert(larger >= smaller)
    assert(larger >= larger)
  }

  @Test
  def <=() = {
    assert(smaller <= larger)
    assert(smaller <= smaller)
  }

  @Test
  def equals() = assertEquals(larger, new Vector2(x1, y1))

  @Test
  def distance() = {
    assertEquals(Vector2.distance(new Vector2(x1,0), Vector2.addIdent), x1)
    assertEquals(Vector2.distance(new Vector2(0,y1), Vector2.addIdent), y1)
    assertEquals(Vector2.distance(new Vector2(x1,y1), Vector2.addIdent),
      math.sqrt(math.pow(x1,2) + math.pow(y1,2)).toLong)
  }

  @Test
  def midpoint() = {
    assertEquals(
      Vector2.addIdent,
      Vector2.midpoint(new Vector2(x1,y1), new Vector2(0-x1, 0-y1))
    )
  }
}
