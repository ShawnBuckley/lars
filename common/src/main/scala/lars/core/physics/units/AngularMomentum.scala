package lars.core.physics.units

import lars.core.math.Vec2

// http://hyperphysics.phy-astr.gsu.edu/hbase/amom.html
case class AngularMomentum(momentum: Momentum, radius: Length) {
  /**
    * Conservation of angular mometum when a body's orbital radius changes.  Happens frequently due to elliptical
    * orbits.
    * @param newRadius new radius
    * @return conserved angular momentum
    */
  def conserve(newRadius: Length): AngularMomentum = {
    val barycenter = Vec2(radius.km, 0)
    val shiftedPosition = momentum.velocity * Time.second
    val towardMagnitude = shiftedPosition.magnitude

    val side = barycenter.distance(shiftedPosition).magnitude
    val angle = 180 - ((Math.pow(radius.km, 2) + Math.pow(towardMagnitude, 2) - Math.pow(side, 2))
      / (2 * radius.km * towardMagnitude))

    val velocity = momentum.velocity * (radius / newRadius) * Math.sin(angle)

    AngularMomentum(Momentum(momentum.mass, velocity), newRadius)
  }

  /**
    * Conservation of angular momentum when a body's mass changes.  Happens any time mass is lost or gained (impact
    * events, massive mining, massive shipment arrival, poaching of material, etc).
    * @param newMass new mass
    * @return conserved angular momentum
    */
  def conserve(newMass: Mass): AngularMomentum = {
    val ratio = momentum.mass / newMass
    AngularMomentum(Momentum(newMass, momentum.velocity * ratio), radius)
  }

//  /**
//    * Conservation of angular momentum when a body's velocity changes. Typically happens during impact events.
//    * @param newVelocity new velocity
//    * @return conserved angular momentum
//    */
//  def conserve(newVelocity: Velocity): AngularMomentum = {
//
//  }
}