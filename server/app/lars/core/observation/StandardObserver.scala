package lars.core.observation

import lars.core.celestial.{Child, Parent}
import lars.core.physics.units.Time

// TODO - handle non-continuous system running
/**
  * The standard observer.
  * @param startTime millis when the simulation started
  * @param timeMulti simulation time multiplier
  * @param maxTickLength max length a single observation can be
  */
class StandardObserver(startTime: Double, timeMulti: Double, maxTickLength: Time) extends Observer {
  /**
    * Converts the current system time into game time.
    * @return current game time
    */
  override def date: Time = {
    convertTime(System.currentTimeMillis())
  }

  /**
    * Converts a system time into game time.
    * @param millis system time
    * @return corresponding game time
    */
  def convertTime(millis: Double): Time = {
    Time(timeMulti * (millis - startTime) / 86400000)
  }

  /**
    * Triggers observation of objects from the source's eldest observable parent.
    * @param child object being observed
    */
  override def observe(child: Child): Unit = {
    def handle(parent: Parent with Child): Unit = {
      getEldest(parent) match {
        case observable: Observable => splitObservations(observable, date)
        case _ =>
      }
    }
    child match {
      case parent: Parent with Child => handle(parent)
      case child: Child =>
        child.parent match {
          case None => // TODO - orphaned object. handle here?
          case Some(parent: Parent) => handle(parent)
        }
      case _ =>
    }
  }

  /**
    * Splits an observation into multiple observations based on the maximum tick length of an observation.
    * @param observable eldest object to start observing from
    * @param date current simulation time
    */
  private def splitObservations(observable: Observable, date: Time): Unit = {
    val difference = date - observable.lastObserved
    for(_ <- 1 to (difference / maxTickLength).toInt) observe(observable, observable.lastObserved + maxTickLength)
    val remainder = difference.d % maxTickLength.d
    if(remainder > 0) observe(observable, observable.lastObserved + (maxTickLength * remainder))
  }

  /**
    * Begins the actual observation. Ensures that all objects in this observation chain are observed at the same time.
    * @param observable eldest object to start observing from
    * @param date current simulation time
    */
  private def observe(observable: Observable, date: Time): Unit = {
    observable.observed(date)
    observable.lastObserved = date
    observable match {
      case _: SelfObservable =>
      case parent: Parent =>
        parent.children.foreach({
          case observable: Observable => observe(observable, date)
          case _ =>
        })
      case _ =>
    }
  }
}
