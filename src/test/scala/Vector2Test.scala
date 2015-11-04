import lars.game.engine.math.Vector2
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
  def +() = assertEquals(larger + smaller, new Vector2(x1 + x2, y1 + y2))

  @Test
  def -() = assertEquals(larger - smaller, new Vector2(x1 - x2, y1 - y2))

  @Test
  def *() = assertEquals(larger * scalar, new Vector2(x1 * scalar, y1 * scalar))

  @Test
  def /() = assertEquals(larger / scalar, new Vector2(x1 / scalar, y1 / scalar))

  @Test
  def >() = assertEquals(larger > smaller, true)

  @Test
  def <() = assertEquals(smaller < larger, true)

  @Test
  def >=() = {
    assertEquals(larger >= smaller, true)
    assertEquals(larger >= larger, true)
  }

  @Test
  def <=() = {
    assertEquals(smaller <= larger, true)
    assertEquals(smaller <= smaller, true)
  }

  @Test
  def equals() = assertEquals(larger, new Vector2(x1, y1))
}
