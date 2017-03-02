package lars.core.math

import lars.core.physics.units.Time
import org.scalatest.FunSuite

class TimeTest extends FunSuite {
  val largerValue = 28.0
  val smallerValue = 16.0

  val larger = Time(largerValue)
  val smaller = Time(smallerValue)

  test("+") {
    assert((larger + smaller).d == largerValue + smallerValue)
  }

  test("-") {
    assert((smaller - larger).d == smallerValue - largerValue)
  }

  test("*") {
    assert((larger * 4).d == largerValue * 4.0)
  }

  test("/") {
    assert((larger / 2).d == largerValue / 2.0)
  }

  test("unary_-") {
    assert(-larger == Time(-larger.d))
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
    assert(larger != smaller)
  }

  test("midpoint") {
    assert(larger.midpoint(smaller) == Time((largerValue + smallerValue) / 2.0))
  }

  test("distance") {
    assert(larger.distance(smaller) == largerValue - smallerValue)
  }

  test("length") {
    assert(smaller.magnitude == smaller.d)
  }
}
