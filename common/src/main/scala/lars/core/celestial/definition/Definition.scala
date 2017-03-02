package lars.core.celestial.definition

case class Definition(name: String,
                      `type`: String,
                      mass: Double,
                      radius: Double,
                      orbit: Orbit,
                      bodies: Seq[Definition])