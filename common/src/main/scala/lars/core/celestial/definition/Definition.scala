package lars.core.celestial.definition

case class Definition(name: String,
                      rank: Int,
                      `type`: String,
                      mass: Double,
                      radius: Option[Double],
                      orbit: Orbit,
                      bodies: Seq[Definition])

object DefinitionOrdering extends Ordering[Definition] {
  override def compare(x: Definition, y: Definition): Int = {
    x.rank.compare(y.rank)
  }
}