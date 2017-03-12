package lars.client

import lars.client.celestial.System
import lars.client.ui.{SystemTable, SystemView}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalajs.dom.window

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport("Client")
class Client extends JSApp {
//  val system = "TRAPPIST-1"
  val systemName = "Sol"

  val systemView = new SystemView("system-view")
  val systemTable = new SystemTable("system-table", systemView)

  systemTable.create(systemName)

  val pauseButton: HTMLButtonElement = dom.document.getElementById("playpause").asInstanceOf[HTMLButtonElement]
  pauseButton.addEventListener("click", (_: dom.Event) => pause())

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
    xhr.onload = { (_: dom.Event) =>
      xhr.responseText match {
        case "true" =>
          pauseButton.textContent = "Stop"
          pauseButton.className = pauseButton.className.replace("btn-success", "btn-danger")
        case "false" =>
          pauseButton.textContent = "Run"
          pauseButton.className = pauseButton.className.replace("btn-danger", "btn-success")
        case _ =>
      }
    }
    xhr.send()
  }

  def requestUpdate(): Unit = {
    val xhr = new dom.XMLHttpRequest
    xhr.open("GET", "rest/system/" + systemName)
    xhr.onload = { (_: dom.Event) =>
      readPlanets(xhr.responseText)
    }
    xhr.send()
  }

  def readPlanets(data: String): Unit = {
    JsonParser.parseSystem(js.JSON.parse(data)) match {
      case None =>
      case Some(system: System) =>
        systemTable.update(system)
        systemView.update(systemName, system)
    }
  }
}