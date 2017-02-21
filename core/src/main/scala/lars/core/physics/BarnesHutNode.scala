package lars.core.physics

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Time, Velocity}

class BarnesHutNode(private var child: Massive, size: Length) extends Barycenter(child.mass, child.location) {
  private var leaves = new Array[BarnesHutNode](4)

  def getQuadrant(point: Vec2): Int = {
    if(point.x <= location.x) {
      if(point.y <= location.y)
        BarnesHutNode.nw
      else
        BarnesHutNode.ne
    } else {
      if(point.y >= location.y)
        BarnesHutNode.se
      else
        BarnesHutNode.sw
    }
  }

  override def add(massive: Massive): Unit = {
    super.add(massive)

    if(child != null) {
      addToQuadrant(child)
      child = null
    }

    addToQuadrant(massive)
  }

  override def add(massives: Seq[Massive]): Unit = {
    massives.foreach(massive => add(massive))
  }

  private def addToQuadrant(massive: Massive): Unit = {
    val quad = getQuadrant(massive.location)
    if(leaves(quad) != null) leaves(quad).add(massive)
    else leaves(quad) = new BarnesHutNode(massive, size/2)
  }

  /**
    * Returns the net forces acting on a body by traversing the tree.
    * @param massive body being acted upon
    * @return forces acting on the body
    */
  def calculate(massive: Massive, time: Time): Velocity = {
    var velocity = Velocity.zero
    if(child != null) {
      if(child != massive)
        velocity += child.gravForce(massive) / massive.mass / time
    }
    else {
      val distance = location.distance(massive.location).magnitude
      if(size.km / distance > BarnesHutNode.theta) {
        leaves.filter(_ != null).foreach(node => {
          velocity += node.calculate(massive, time)
        })
      } else {
        velocity += gravForce(massive) / massive.mass / time
      }
    }
    velocity
  }
}

object BarnesHutNode {
  /**
    * Ratio used to determine if a node is abstractly far away from a body and just the center of mass can be used to
    * calculate forces acting on it.  The default value of 0.5 means that a body must be 2x the distance of node's size
    * in order for it to be abstractly far away.
    */
  val theta = 0.25

  val nw = 0
  val ne = 1
  val se = 2
  val sw = 3
}