package lars.game.engine.celestial.body

import lars.game.engine.celestial.Massive
import lars.game.engine.math.{Vector2, Physics}

import scala.collection.mutable.ArrayBuffer

/**
 * This is a class that is used to create a virtual binary+ system of massive objects.  It takes in an array of massives
 * and calculates what the center point of the provided objects as.
 *
 * @param massives The components to make the system
 */
class MassiveSystem(massives: ArrayBuffer[Massive]) extends Massive {
  var barycenter = Physics.barycenter(massives)

  /**
   * Returns the total mass of the system.  Note: it is possible to double the mass value in calculations when
   * calling this method and and the mass of its components together.  Use just one approach to find the mass of the
   * system.
   * @return total mass of the system
   */
  override def mass(): Long = massives.reduceLeft{ _.mass + _.mass }

  /**
   * Returns the barycenter for the system
   * @return system barycenter
   */
  override def location(): Vector2 = barycenter

  /**
   * Moves all objects in the system to the new location.  Positions relative to each other are maintained.
   * @param loc new barycenter
   */
  override def location_(loc: Vector2): Unit = {
    val diff = barycenter - loc
    barycenter = loc
    var i = 0
    while(i < massives.length) {
      val massive = massives(i)
      massive.location_(massive.location + diff)
      i += 1
    }
  }

  /**
   * This observes all of the objects in the system.
   */
  override def observe(): Unit = {
    var i = 0
    while(i < massives.length) {
      massives(i).observe()
      i += 1
    }
  }
}
