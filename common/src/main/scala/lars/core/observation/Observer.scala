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
    * @param child object to observe
    */
  def observe(child: Child): Unit

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
          case self: SelfObservable =>
            observe(self)
            child
          case _: Unobservable => child
          case _ => getEldest(parent)
        }
    }
  }
}
