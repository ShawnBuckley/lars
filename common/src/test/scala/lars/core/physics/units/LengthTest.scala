package lars.core.math

import lars.core.physics.units.{Area, Length, Speed, Time}
import org.scalatest.FunSuite

class LengthTest extends FunSuite {
  val largerValue = 28.0
  val smallerValue = 16.0

  val larger = Length(largerValue)
  val smaller = Length(smallerValue)

  test("+") {
    assert((larger + smaller).km == largerValue + smallerValue)
  }

  test("-") {
    assert((smaller - larger).km == smallerValue - largerValue)
  }

  test("*") {
    assert((larger * 4).km == largerValue * 4.0)
  }

  test("/") {
    assert((larger / 2).km == largerValue / 2.0)
  }

  test("unary_-") {
    assert(-larger == Length(-larger.km))
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
    assert(smaller <= larger)
  }

  test("==") {
    assert(larger == larger)
  }

  test("!=") {
    assert(larger != smaller)
  }

  test("midpoint") {
    assert(larger.midpoint(smaller) == Length((largerValue + smallerValue) / 2.0))
  }

  test("distance") {
    assert(larger.distance(smaller) == largerValue - smallerValue)
  }

  test("length") {
    assert(smaller.magnitude == smaller.km)
  }

  // Other unit conversions

  test("divide by length") {
    assert(larger / smaller == largerValue / smallerValue)
  }

  test("multiply by length") {
    assert(larger * smaller == Area(largerValue * smallerValue))
  }

  test("divide by time") {
    assert(larger / Time.seconds(1) == new Speed(largerValue * 1000))
  }
}
