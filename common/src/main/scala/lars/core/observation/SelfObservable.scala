package lars.core.observation

/**
  * Self observable objects are parent objects that don't observe their children when observed. The galaxy is one.
  */
trait SelfObservable extends Observable
