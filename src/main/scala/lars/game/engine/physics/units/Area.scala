package lars.game.engine.physics.units

import lars.game.engine.Types.DistanceType

class Area(width: Length, height: Length) {
  val area = width.km * height.km

  def km2: DistanceType =
    area
}
