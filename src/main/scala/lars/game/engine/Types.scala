package lars.game.engine

object Types {
  type MassType = Long
  type DistanceType = Long
  type VolumeType = Double
  type DensityType = Double

  def toMass[T](value: T): MassType =
    value.asInstanceOf[Number].longValue

  def toDistance[T](value: T): DistanceType =
    value.asInstanceOf[Number].longValue

  def toVolume[T](value: T): VolumeType =
    value.asInstanceOf[Number].doubleValue

  def toDensity[T](value: T): DensityType =
    value.asInstanceOf[Number].doubleValue
}
