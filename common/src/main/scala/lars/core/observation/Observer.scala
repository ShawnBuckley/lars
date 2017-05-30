package lars.core.observation

import lars.core.celestial.{Child, Parent}
import lars.core.physics.units.Time

import scala.annotation.tailrec

/**
  * Observes observable objects
  */
trait Observer {
  /**
    * Runs the simulation clock.
    */
  def start(): Unit

  /**
    * Stops the simulation clock.
    */
  def stop(): Unit

  /**
    * Starts or stops the simulation clock and returns the current state.
    * @return true is running
    */
  def pause(): Boolean

  /**
    * Gets the current state of the observer.
    * @return true is running
    */
  def isRunning: Boolean

  /**
    * Creates an observation chain starting from a single observable object. The chain will cascade until it reaches
    * the youngest most objects and the eldest most or unobservable object.
    * @param observable object to observe
    * @return the object after observation
    */
  def observe(observable: Observable): Observable

  /**
    * Gets the current simulation date
    * @return current simulation date
    */
  def date: Time

  /**
    * Returns the eldest most observable object
    * @param current child
    * @return eldest observable object
    */
  @tailrec final def getEldest(current: Parent): Parent = {
    current match {
      case child: Child =>
        child.parent match {
          case _: Unobservable =>
            current
          case self: SelfObservable =>
            observe(self)
            child
          case _ =>
            child.parent match {
              case Some(parent) => getEldest(parent)
              case None => current
            }
        }
      case _ => current
    }
  }
}
