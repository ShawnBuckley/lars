package lars.game.engine.celestial

trait Child {
  var par: Parent

  /**
   * Returns the parent
   * @return parent
   */
  def parent: Parent =
    par

  /**
   * Sets a new parent.
   * @param par new parent
   */
  def parent_=(par: Parent) = this.par = par
}
