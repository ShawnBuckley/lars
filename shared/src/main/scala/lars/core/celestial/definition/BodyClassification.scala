package lars.core.celestial.definition

object BodyClassification extends Enumeration {
  type BodyClassification = Value
  val singularity = Value("singularity")
  val stellar = Value("stellar")
  val gaseous = Value("gaseous")
  val terrestrial = Value("terrestrial")
  val micro = Value("micro")
  val none = Value("none")
}