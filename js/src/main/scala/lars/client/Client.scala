package lars.client

import lars.client.celestial.CelestialBody
import lars.client.ui.{SystemTable, SystemView}
import lars.core.math.Vec2
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
  pauseButton.addEventListener("click", (_: dom.Event) => pause())

  val system = "Sol"

  // refresh rate
  val fps = 10.0
  val rate: Double = (1.0 / fps) * 1000.0

  @JSExport
  override def main(): Unit = {
    window.setInterval(() => {
      requestUpdate()
    }, rate)
  }

  def pause(): Unit = {
    val xhr = new dom.XMLHttpRequest
    xhr.open("POST", "rest/game/pause")
    xhr.send()
  }

  def requestUpdate(): Unit = {
    val xhr = new dom.XMLHttpRequest
    xhr.open("GET", "rest/system/" + system)
    xhr.onload = { (_: dom.Event) =>
      readPlanets(xhr.responseText)
    }
    xhr.send()
  }

  def readPlanets(data: String): Unit = {
    JsonParser.parseSystem(js.JSON.parse(data), Vec2.addIdent) match {
      case None =>
      case Some(bodies: Seq[CelestialBody]) =>
        println("length " + bodies.length)
        systemTable.update(bodies)
        systemView.update(bodies)
    }
  }
}