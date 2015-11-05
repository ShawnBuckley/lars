import lars.game.engine.celestial.Massive
import lars.game.engine.math.Vector2
import org.testng.annotations.Test
import org.testng.Assert._

class MassiveTest {
  class MassiveTestImpl(m: Long) extends Massive {
    override def mass: Long = m
    override def location_=(loc: Vector2): Unit = ???
    override def location: Vector2 = ???
    override def observe(): Unit = ???
    override def drift_=(vec: Vector2): Unit = ???
    override def drift: Vector2 = ???
  }

  val smaller = new MassiveTestImpl(1000)
  val larger = new MassiveTestImpl(10000)

  @Test
  def >() = assert(smaller < larger)

  @Test
  def <() = assert(larger > smaller)

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
    assertEquals(Massive.min(smaller, larger), smaller)
    assertEquals(Massive.min(larger, smaller), smaller)
  }

  @Test
  def max() = {
    assertEquals(Massive.max(larger, smaller), larger)
    assertEquals(Massive.max(smaller, larger), larger)
  }

  @Test
  def sort() = {
    val result = (larger, smaller)
    assertEquals(Massive.sort(larger, smaller), result)
    assertEquals(Massive.sort(smaller, larger), result)
  }
}
