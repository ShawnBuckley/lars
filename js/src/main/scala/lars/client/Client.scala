package lars.client

import lars.client.celestial.CelestialBody
import lars.client.ui.{SystemTable, SystemView}
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
    val bodies = new Array[CelestialBody](parsed.length)
    for(i <- bodies.indices)
      bodies(i) = JsonParser.parseCelestialBody(parsed(i))

    systemTable.update(bodies)
    systemView.update(bodies)
  }
}