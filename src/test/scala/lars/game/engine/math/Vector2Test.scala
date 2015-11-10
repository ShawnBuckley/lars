package lars.game.engine.math

import lars.game.engine.physics.units.Length.LengthType
import org.testng.annotations.Test
import org.testng.Assert._

class Vector2Test {
  val x1: LengthType = 14
  val y1: LengthType = 5
  val larger = new Vector2(x1, y1)

  val x2: LengthType = 3
  val y2: LengthType = 6
  val smaller = new Vector2(x2, y2)

  val scalar: LengthType = 2

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
  def negative() = {
    assertEquals(-smaller, new Vector2(-smaller.x, -smaller.y))
    assertEquals(-larger, new Vector2(-larger.x, -larger.y))
  }

  @Test
  def equals() = assertEquals(larger, new Vector2(x1, y1))

  @Test
  def distance() = {
    assertEquals(Vector2.distance(new Vector2(x1,0), Vector2.addIdent), x1)
    assertEquals(Vector2.distance(new Vector2(0,y1), Vector2.addIdent), y1)
    assertEquals(Vector2.distance(new Vector2(x1,y1), Vector2.addIdent),
      math.sqrt(math.pow(x1,2) + math.pow(y1,2)))
  }

  @Test
  def midpoint() = {
    assertEquals(
      Vector2.addIdent,
      Vector2.midpoint(new Vector2(x1,y1), new Vector2(0-x1, 0-y1))
    )
  }

  @Test
  def normalize() = {
    val longer: LengthType = math.abs(larger.length)
    val shorter: LengthType = math.abs(smaller.length)
    assertEquals(larger.normalize, new Vector2(x1 / longer, y1 / longer))
    assertEquals(smaller.normalize, new Vector2(x2 / shorter, y2 / shorter))
  }
}
