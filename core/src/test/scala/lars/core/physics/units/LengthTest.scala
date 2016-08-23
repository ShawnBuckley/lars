package lars.core.math

import lars.core.physics.units.{Area, Length, Speed, Time}
import org.testng.annotations.Test
import org.testng.Assert._

class LengthTest {
  val largerValue = 28.0
  val smallerValue = 16.0

  val larger = Length(largerValue)
  val smaller = Length(smallerValue)

  @Test def + = assertEquals((larger + smaller).km, largerValue + smallerValue)
  @Test def - = assertEquals((smaller - larger).km, smallerValue - largerValue)
  @Test def * = assertEquals((larger * 4).km, largerValue * 4.0)
  @Test def / = assertEquals((larger / 2).km, largerValue / 2.0)

  @Test def unary_- = assertEquals(-larger, Length(-larger.km))

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
    assert(smaller <= larger)
  }

  @Test def equals = {
    assert(larger == larger)
    assert(larger != smaller)
  }

  @Test def midpoint = assertEquals(larger.midpoint(smaller), Length((largerValue + smallerValue) / 2.0))
  @Test def distance = assertEquals(larger.distance(smaller), largerValue - smallerValue)
  @Test def length = assertEquals(smaller.magnitude, smaller.km)

  // Other unit conversions

  @Test def divideLength() = assertEquals(larger / smaller, largerValue / smallerValue)

  @Test def multiplyLength() = assertEquals(larger * smaller, new Area(largerValue * smallerValue))

  @Test def divideTime() = assertEquals(larger / Time.seconds(1), new Speed(largerValue * 1000))
}
