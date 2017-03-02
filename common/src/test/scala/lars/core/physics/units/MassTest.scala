package lars.core.physics.units

import org.scalatest.FunSuite

class MassTest extends FunSuite {
  val smaller = new Mass(1000)
  val larger =  new Mass(10000)

  test("+") {
    assert(smaller + larger == new Mass(smaller.kg + larger.kg))
  }

  test("-") {
    assert(larger - smaller == new Mass(larger.kg - smaller.kg))
  }

  test("*") {
    assert(smaller * 2 == Mass(smaller.kg * 2))
  }

  test("/") {
    assert(smaller / 2 == Mass(smaller.kg / 2))
  }

  test("<") {
    assert(smaller < larger)
  }

  test(">") {
    assert(larger > smaller)
  }

  test(">=") {
    assert(larger >= smaller)
    assert(smaller >= smaller)
  }

  test("<=") {
    assert(smaller <= larger)
    assert(smaller <= smaller)
  }

  test("==") {
    assert(smaller == Mass(smaller.kg))
  }

  test("min") {
    assert(Mass.min(smaller, larger) == smaller)
    assert(Mass.min(larger, smaller) == smaller)
  }

  test("max") {
    assert(Mass.max(larger, smaller) == larger)
    assert(Mass.max(smaller, larger) == larger)
  }

  test("density") {
    val volume = Volume(10)
    assert(larger / volume == Density(1000))
  }
}
