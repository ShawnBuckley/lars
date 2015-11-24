package lars.game.engine.physics.units

import lars.game.engine.math.Vector2
import lars.game.engine.physics.units.Length.LengthType

case class Velocity(ms: Vector2) {
  def kms: Vector2 =
    ms / 1000

  def +(that: Velocity): Velocity =
    new Velocity(ms + that.ms)

  def *(that: LengthType): Velocity =
    new Velocity(ms * that)

  def speed: Speed =
    new Speed(ms.length)
}

object Velocity {
  val zero = new Velocity(Vector2.addIdent)
}