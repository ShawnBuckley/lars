package lars

import lars.client.Body
import lars.core.math.Vec2
import lars.core.physics.units.{Length, Mass, Velocity}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalajs.dom.window

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport("Client")
class Client extends JSApp {
  val systemView = new SystemView("system-view")
  val systemTable = new SystemTable("system-table", systemView)

  val pauseButton: HTMLButtonElement = dom.document.getElementById("playpause").asInstanceOf[HTMLButtonElement]
  pauseButton.addEventListener("click", (event: dom.Event) => pause())

  val system = "Sol"

  // refresh rate
  val fps = 10
  val rate: Double = (1 / fps) * 1000.0
  println(rate)

  @JSExport
  override def main(): Unit = {
    window.setInterval(() => {
      requestUpdate()
    }, 100.0)
  }

  def pause(): Unit = {
    val xhr = new dom.XMLHttpRequest
    xhr.open("POST", "rest/game/pause")
    xhr.send()
  }

  def requestUpdate(): Unit = {
    val xhr = new dom.XMLHttpRequest
    xhr.open("GET", "rest/system/" + system)
    xhr.onload = { (e: dom.Event) =>
      readPlanets(xhr.responseText)
    }
    xhr.send()
  }

  def readPlanets(data: String): Unit = {
    val parsed = js.JSON.parse(data).asInstanceOf[js.Array[js.Dynamic]]
    val bodies = new Array[Body](parsed.length)
    for(i <- bodies.indices)
      bodies(i) = Client.convertBody(parsed(i))

    systemTable.update(bodies)
    systemView.update(bodies)
  }
}

object Client {
  def convertVec2(data: js.Dynamic): Vec2 = {
    Vec2(data.x.asInstanceOf[Double],
      data.y.asInstanceOf[Double])
  }

  def convertBody(data: js.Dynamic): Body = {
    Body(
      Some(data.name.asInstanceOf[String]),
      Mass(data.mass.kg.asInstanceOf[Double]),
      convertVec2(data.location),
      Velocity(convertVec2(data.velocity.ms)),
      Length(data.size.km.asInstanceOf[Double])
    )
  }
}