package lars

object Main {
  def main(args: Array[String]) {
    val webapp = new EmbeddedWebapp
    webapp.start()
  }
}
