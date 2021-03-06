package lars.core.observation

import lars.core.celestial.{Child, Parent}
import lars.core.physics.units.Time

/**
  * The standard observer.
  * @param timeMulti simulation time multiplier
  * @param maxTickLength max length a single observation can be
  */
class StandardObserver(timeMulti: Double, maxTickLength: Time) extends Observer {
  private var startTime: Double = 0
  private var stopTime: Double = 0
  private var offset: Time = Time.zero
  private var running = false

  /**
    * Runs the simulation clock.
    */
  override def start(): Unit = {
    startTime = System.currentTimeMillis()
    running = true
  }

  /**
    * Stops the simulation clock.
    */
  override def stop(): Unit = {
    stopTime = System.currentTimeMillis()
    offset = convertTime(stopTime)
    running = false
  }

  /**
    * Starts or stops the simulation clock
    */
  override def pause(): Boolean = {
    if(running)
      stop()
    else
      start()
    running
  }

  /**
    * Gets the current state of the observer.
    *
    * @return true is running
    */
  override def isRunning: Boolean = running

  /**
    * Converts the current system time into game time.
 *
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
    val difference = {
      if(running)
        millis - startTime
      else
        stopTime - startTime
    }

    offset + Time(timeMulti * difference / 86400000)
  }

  /**
    * Triggers observation of objects from the source's eldest observable parent.
    * @param observable object being observed
    */
  override def observe(observable: Observable): Observable = {
    if(!running) return observable
    def handle(parent: Parent with Child): Unit = {
      getEldest(parent) match {
        case observable: Observable => splitObservations(observable, date)
        case _ =>
      }
    }
    observable match {
      case _: Unobservable =>
      case parent: Parent with Child => handle(parent)
      case child: Child =>
        child.parent match {
          case Some(parent) => parent match {
            case parent: Child => handle(parent)
            case _ =>
          }
          case None =>
        }
      case _ =>
    }
    observable
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
