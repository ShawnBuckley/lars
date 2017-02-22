package lars.core.physics

import lars.core.celestial.Massive
import lars.core.physics.units.Force

/**
  * Force calculator used for determining the gravitational forces acting on a body in a system
  */
trait ForceCalculator {
  /**
    * Gets the barycenter of the system.
    * @return barycenter
    */
  def barycenter: Barycenter

  /**
    * Returns the gravitational forces other bodies are acting upon a body in the system.
    * @param massive body acted upon
    * @return gravitational force
    */
  def calculate(massive: Massive): Force
}
