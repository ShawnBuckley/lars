package lars.game.engine

case class Gametime(time: Long) {
  def +(other: Int): Gametime =
    new Gametime(time + other)
}
