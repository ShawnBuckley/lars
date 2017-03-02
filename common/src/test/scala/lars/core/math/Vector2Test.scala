package lars.core.math

import org.testng.annotations.Test
import org.testng.Assert._

class Vec2Test {
  val largerX: Double = 14
  val largerY: Double = 5
  val larger = new Vec2(largerX, largerY)

  val smallerX: Double = 3
  val smallerY: Double = 6
  val smaller = new Vec2(smallerX, smallerY)

  val scalar: Double = 2

  @Test def + = assertEquals(larger + smaller, new Vec2(largerX + smallerX, largerY + smallerY))
  @Test def - = assertEquals(larger - smaller, new Vec2(largerX - smallerX, largerY - smallerY))
  @Test def * = assertEquals(larger * scalar, new Vec2(largerX * scalar, largerY * scalar))
  @Test def / = assertEquals(larger / scalar, new Vec2(largerX / scalar, largerY / scalar))

  @Test
  def unary_- = assertEquals(-smaller, new Vec2(-smaller.x, -smaller.y))

  @Test def > = {
    assert(larger > smaller)
    assert(!(smaller > larger))
    assert(!(larger > larger))
  }

  @Test def < = {
    assert(smaller < larger)
    assert(!(larger < smaller))
    assert(!(smaller < smaller))
  }

  @Test def >= = {
    assert(larger >= smaller)
    assert(!(smaller >= larger))
    assert(larger >= larger)
  }

  @Test def <= = {
    assert(smaller <= larger)
    assert(!(larger <= smaller))
    assert(smaller <= smaller)
  }

  @Test def equals = assertEquals(larger, new Vec2(largerX, largerY))

  @Test def distance = {
    assertEquals(Vec2(largerX,0).distance(Vec2.addIdent), Vec2(largerX, 0.0))
    assertEquals(Vec2(0,largerY).distance(Vec2.addIdent), Vec2(0.0, largerY))
  }

  @Test def midpoint = assertEquals(larger.midpoint(smaller), Vec2((largerX + smallerX)/2, (largerY + smallerY)/2))

  @Test def normalize = {
    val longer: Double = math.abs(larger.magnitude)
    val shorter: Double = math.abs(smaller.magnitude)
    assertEquals(larger.normalize, new Vec2(largerX / longer, largerY / longer))
    assertEquals(smaller.normalize, new Vec2(smallerX / shorter, smallerY / shorter))
  }
}
