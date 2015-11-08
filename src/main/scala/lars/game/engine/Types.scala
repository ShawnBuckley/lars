package lars.game.engine

object Types {
  type MassType = Double
  type DistanceType = Double
  type VolumeType = Double
  type DensityType = Double
  type TimeType = Double
  type TemperatureType = Double
  type SpeedType = Double

  val zeroMass = toMass(0)
  val zeroDistance = toDistance(0)
  val oneDistance = toDistance(1)
  val zeroSpeed = toSpeed(0)

  // Any numeric type conversions

  def toMass(value: AnyVal): MassType =
    value.asInstanceOf[Number].doubleValue

  def toDistance(value: AnyVal): DistanceType =
    value.asInstanceOf[Number].doubleValue

  def toVolume(value: AnyVal): VolumeType =
    value.asInstanceOf[Number].doubleValue

  def toDensity(value: AnyVal): DensityType =
    value.asInstanceOf[Number].doubleValue

  def toTime(value: AnyVal): TimeType =
    value.asInstanceOf[Number].doubleValue

  def toTemperature(value: AnyVal): TemperatureType =
    value.asInstanceOf[Number].doubleValue

  def toSpeed(value: AnyVal): SpeedType =
    value.asInstanceOf[Number].doubleValue

  // Performance optimization conversion.
  // Happens when the type to be converted is the same.

  def toMass(value: MassType): MassType =
    value

  def toDistance(value: DistanceType): DistanceType =
    value

  def toVolume(value: VolumeType): VolumeType =
    value

  def toDensity(value: DensityType): DensityType =
    value

  def toTime(value: TimeType): TimeType =
    value

  def toTemperature(value: TemperatureType): TemperatureType =
    value

  def toSpeed(value: SpeedType): SpeedType =
    value
}
