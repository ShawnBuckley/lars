package lars.game.engine.math

object Util {
  def sum(start: Int, end: Int, callback: (Int) => Long): Long = {
    var i = start
    var total = 0L
    while(i < end) {
      total += callback(i)
      i += 1
    }
    total
  }
}
