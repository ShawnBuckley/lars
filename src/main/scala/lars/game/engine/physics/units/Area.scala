package lars.game.engine.physics.units

import lars.game.engine.physics.units.Area.AreaType

case class Area(val km2: AreaType) {
  def this(width: Length, height: Length) =
    this(width.km * height.km)
}

object Area {
  type AreaType = Double
}