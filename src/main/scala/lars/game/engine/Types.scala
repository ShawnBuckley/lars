package lars.game.engine

object Types {
  type MassType = Double
  type DistanceType = Long
  type VolumeType = Double
  type DensityType = Double

  val zeroMass = toMass(0)
  val zeroDistance = toDistance(0)

  def toMass(value: AnyVal): MassType =
    value.asInstanceOf[Number].doubleValue

  def toDistance(value: AnyVal): DistanceType =
    value.asInstanceOf[Number].longValue

  def toVolume(value: AnyVal): VolumeType =
    value.asInstanceOf[Number].doubleValue

  def toDensity(value: AnyVal): DensityType =
    value.asInstanceOf[Number].doubleValue
}
