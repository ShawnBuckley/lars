package lars.core.math

import org.scalatest.FunSuite

class Vec2Test extends FunSuite {
  val largerX: Double = 14
  val largerY: Double = 5
  val larger = new Vec2(largerX, largerY)

  val smallerX: Double = 3
  val smallerY: Double = 6
  val smaller = new Vec2(smallerX, smallerY)

  val scalar: Double = 2

  test("+") {
    assert(larger + smaller == new Vec2(largerX + smallerX, largerY + smallerY))
  }
  
  test("-") {
    assert(larger - smaller == new Vec2(largerX - smallerX, largerY - smallerY))
  }
  
  test("*") {
    assert(larger * scalar == new Vec2(largerX * scalar, largerY * scalar))
  }
  
  test("/") {
    assert(larger / scalar == new Vec2(largerX / scalar, largerY / scalar))
  }

  test("unary_-") {
    assert(-smaller == new Vec2(-smaller.x, -smaller.y))
  }

  test(">") {
    assert(larger > smaller)
    assert(!(smaller > larger))
    assert(!(larger > larger))
  }

  test("<") {
    assert(smaller < larger)
    assert(!(larger < smaller))
    assert(!(smaller < smaller))
  }

  test(">=") {
    assert(larger >= smaller)
    assert(!(smaller >= larger))
    assert(larger >= larger)
  }

  test("<=") {
    assert(smaller <= larger)
    assert(!(larger <= smaller))
    assert(smaller <= smaller)
  }

  test("==") {
    assert(larger == new Vec2(largerX, largerY))
  }

  test("distance") {
    assert(Vec2(largerX,0).distance(Vec2.addIdent) == Vec2(largerX, 0.0))
    assert(Vec2(0,largerY).distance(Vec2.addIdent) == Vec2(0.0, largerY))
  }

  test("midpoint") {
    assert(larger.midpoint(smaller) == Vec2((largerX + smallerX)/2, (largerY + smallerY)/2))
  }

  test("normalize") {
    val longer: Double = math.abs(larger.magnitude)
    val shorter: Double = math.abs(smaller.magnitude)
    assert(larger.normalize == new Vec2(largerX / longer, largerY / longer))
    assert(smaller.normalize == new Vec2(smallerX / shorter, smallerY / shorter))
  }
}
