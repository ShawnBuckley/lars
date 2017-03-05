package lars.core.physics.celestial.gravitation.barneshut

import lars.core.celestial.Massive
import lars.core.math.Vec2
import lars.core.physics.Barycenter
import lars.core.physics.units._

class BarnesHutNode(private var child: Massive, size: Length) extends Barycenter(child.mass, child.location) {
  private var leaves: Array[BarnesHutNode] = _

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
    if(leaves == null) leaves = new Array[BarnesHutNode](4)
    val quad = getQuadrant(massive.location)
    if(leaves(quad) != null) leaves(quad).add(massive)
    else leaves(quad) = new BarnesHutNode(massive, size/2)
  }

  /**
    * Returns the net forces acting on a body by traversing the tree.
    * @param massive body being acted upon
    * @return forces acting on the body
    */
  def  calculate(massive: Massive): Force = {
    var force = Force.zero
    if(child != null) {
      if(child != massive)
        force += child.gravForce(massive)
    }
    else {
      val distance = location.distance(massive.location).magnitude
      if(size.km / distance > BarnesHutNode.theta) {
        leaves.filter(_ != null).foreach(node => {
          force += node.calculate(massive)
        })
      } else {
        force += gravForce(massive)
      }
    }
    force
  }
}

object BarnesHutNode {
  /**
    * Ratio used to determine if a node is abstractly far away from a body and just the center of mass can be used to
    * calculate forces acting on it.  The default value of 0.5 means that a body must be 2x the distance of node's size
    * in order for it to be abstractly far away.
    */
  val theta = 0.5

  val nw = 0
  val ne = 1
  val se = 2
  val sw = 3
}