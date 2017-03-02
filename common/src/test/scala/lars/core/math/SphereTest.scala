package lars.core.math

import lars.core.physics.units.{Length, Volume}
import org.scalatest.FunSuite

class SphereTest extends FunSuite {
  test("volume") {
    assert(Sphere.volume(new Length(10)) == Volume(math.Pi * 1e3))
  }
}
