import lars.game.engine.celestial.Massive
import lars.game.engine.celestial.body.MassiveBody
import lars.game.engine.math.Vector2
import org.testng.annotations.Test
import org.testng.Assert._

class MassiveTest {
  val smaller = new MassiveBody(1000, Vector2.addIdent)
  val larger = new MassiveBody(10000, Vector2.addIdent)

  @Test
  def >() = assertEquals(smaller < larger, true)

  @Test
  def <() = assertEquals(larger > smaller, true)

  @Test
  def >=() = {
    assertEquals(larger >= smaller, true)
    assertEquals(smaller >= smaller, true)
  }

  @Test
  def <=() = {
    assertEquals(smaller <= larger, true)
    assertEquals(smaller <= smaller, true)
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
