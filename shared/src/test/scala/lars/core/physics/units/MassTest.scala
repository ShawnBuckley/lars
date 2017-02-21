package lars.core.physics.units

import org.testng.Assert._
import org.testng.annotations.Test

class MassTest {
  val smaller = new Mass(1000)
  val larger =  new Mass(10000)

  @Test
  def +(): Unit = {
    assertEquals(smaller + larger, Mass(11000))
  }

  @Test
  def -(): Unit = {
    assertEquals(larger - smaller, Mass(9000))
  }

  @Test
  def *(): Unit = {
    assertEquals(smaller * 2, Mass(2000))
  }

  @Test
  def /(): Unit = {
    assertEquals(smaller / 2, Mass(500))
  }

  @Test
  def >() =
    assert(smaller < larger)

  @Test
  def <() =
    assert(larger > smaller)

  @Test
  def >=() = {
    assert(larger >= smaller)
    assert(smaller >= smaller)
  }

  @Test
  def <=() = {
    assert(smaller <= larger)
    assert(smaller <= smaller)
  }

  @Test
  def min() = {
    assertEquals(Mass.min(smaller, larger), smaller)
    assertEquals(Mass.min(larger, smaller), smaller)
  }

  @Test
  def max() = {
    assertEquals(Mass.max(larger, smaller), larger)
    assertEquals(Mass.max(smaller, larger), larger)
  }

  @Test
  def sort() = {
    val result = (larger, smaller)
    assertEquals(Mass.sort(larger, smaller), result)
    assertEquals(Mass.sort(smaller, larger), result)
  }

  @Test
  def density() = {
    val volume = new Volume(10)
    assertEquals(larger / volume, new Density(1000))
  }
}