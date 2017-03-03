package lars.core.observation

import lars.core.celestial.{Child, Parent}
import lars.core.physics.units.Time

import scala.annotation.tailrec

/**
  * Observes observable objects
  */
trait Observer {
  /**
    * Creates an observation chain starting from a single observable object. The chain will cascade until it reaches
    * the youngest most objects and the eldest most or unobservable object.
    * @param observable object to observe
    */
  def observe(observable: Observable): Unit

  /**
    * Gets the current simulation date
    * @return current simulation date
    */
  def date: Time

  /**
    * Returns the eldest most observable object
    * @param child child
    * @return eldest observable object
    */
  @tailrec final def getEldest(child: Parent with Child): Parent with Child = {
    child.parent match {
      case None => child
      case Some(parent: Parent) =>
        parent match {
          case _: Unobservable => child
          case _ => getEldest(parent)
        }
    }
  }
}
