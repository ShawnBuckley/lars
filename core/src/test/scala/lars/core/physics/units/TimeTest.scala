package lars.core.math

import lars.core.physics.units.Time
import org.testng.annotations.Test
import org.testng.Assert._

class TimeTest {
  val largerValue = 28.0
  val smallerValue = 16.0

  val larger = Time(largerValue)
  val smaller = Time(smallerValue)

  @Test def + = assertEquals((larger + smaller).d, largerValue + smallerValue)
  @Test def - = assertEquals((smaller - larger).d, smallerValue - largerValue)
  @Test def * = assertEquals((larger * 4).d, largerValue * 4.0)
  @Test def / = assertEquals((larger / 2).d, largerValue / 2.0)

  @Test def unary_- = assertEquals(-larger, Time(-larger.d))

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

  @Test def midpoint = assertEquals(larger.midpoint(smaller), Time((largerValue + smallerValue) / 2.0))
  @Test def distance = assertEquals(larger.distance(smaller), largerValue - smallerValue)
  @Test def length = assertEquals(smaller.magnitude, smaller.d)
}
