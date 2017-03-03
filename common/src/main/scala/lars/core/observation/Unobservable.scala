package lars.core.observation
import lars.core.physics.units.Time

/**
  * Unobservable objects are objects that should not be observed by observers. These will be used as barriers in
  * observation chains and will include objects too large for observation such as the galaxy.
  */
trait Unobservable extends Observable {
  override def observed(date: Time): Unit = {
    // do nothing
    // TODO - log / exception?
  }
}