package lars.game.engine.celestial

import lars.game.engine.math.Sphere
import lars.game.engine.physics.units.{Density, Length}

/**
  * Sizeable objects are the second type of objects that can exist in space.  They are massive objects that have a
  * definite size.  Size is variable as objects may have size changes due events such as the ejection of stellar
  * material, planetary cataclysm due to advanced weapons of mass destruction, or orbital collisions.
  *
  * Sizeable objects also gain the ability to collide with other sizeable objects.  The default behavior for collisions
  * is nothing - that is determined entirely by the specific types of bodies that collide.  This behavior will vary
  * greatly for the different types of standard bodies (micro, gaseous, terrestrial, stellar, singularity).
  */
trait Sizeable extends Massive {
  var size: Length

  def density: Density =
    new Density(mass.kg / Sphere.volume(size).km3)

  def collide(other: Sizeable)
}
