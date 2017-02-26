package lars.client.ui

import lars.client.Client
import lars.client.celestial.CelestialBody
import lars.core.math.Polar2
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.raw.{HTMLButtonElement, HTMLTableCellElement, HTMLTableElement, HTMLTableRowElement}

import scala.scalajs.js

class SystemTable(elementId: String, view: SystemView) {
  val table: HTMLTableElement = document.getElementById(elementId).asInstanceOf[HTMLTableElement]

  val au = 1.496e8
  val lunaDist = 384402

  val system = "Sol"

  // create table

  val xhr = new dom.XMLHttpRequest()
  xhr.open("GET", "rest/system/" + system)
  xhr.onload = { (e: dom.Event) =>
    if(xhr.status == 200) {
      val parsed = js.JSON.parse(xhr.responseText).asInstanceOf[js.Array[js.Dynamic]]
      parsed.foreach(data => {
        val body = Client.convertBody(data)
        val row = table.insertRow().asInstanceOf[HTMLTableRowElement]
        row.id = "system_table_" + body.name.get

        val nameCell = row.insertCell().asInstanceOf[HTMLTableCellElement]
        body.name match {
          case None =>
            nameCell.textContent = "Unnamed"
          case Some(name: String) =>
            val bodyButton = dom.document.createElement("button").asInstanceOf[HTMLButtonElement]
            bodyButton.textContent = name
            bodyButton.addEventListener("click", (event: dom.Event) =>
              view.focus(name)
            )
            nameCell.appendChild(bodyButton)
        }

        val x = row.insertCell().asInstanceOf[HTMLTableCellElement]
        x.className = "planet-location"

        val y = row.insertCell().asInstanceOf[HTMLTableCellElement]
        y.className = "planet-location"

        val button = document.createElement("button").asInstanceOf[HTMLButtonElement]
        button.className = "planet-button"
        button.appendChild(document.createTextNode(body.name.get))
        button.addEventListener("click", { (event: dom.Event) =>
          view.focus(body.name.get)
        })
      })
    }
  }
  xhr.send()

  def update(bodies: Array[CelestialBody]): Unit = {
    // handle sun absolute location
    val sol = bodies(0)
    val solRow = table.rows.namedItem("system_table_" + sol.name.get).asInstanceOf[HTMLTableRowElement]
    solRow.cells.item(1).textContent = (sol.location.x / au).toString
    solRow.cells.item(2).textContent = (sol.location.y / au).toString

    // handle luna
    bodies.find(body => body.name.getOrElse("") == "Earth") match {
      case None =>
      case Some(earth: CelestialBody) =>
        bodies.find(body => body.name.getOrElse("") == "Luna") match {
          case None =>
          case Some(luna: CelestialBody) =>
            val lunaPolar = Polar2.convert(earth.location, luna.location)

            val lunaRow = table.rows.namedItem("system_table_" + luna.name.get).asInstanceOf[HTMLTableRowElement]
            lunaRow.cells.item(1).textContent = lunaPolar.angle.toString
            lunaRow.cells.item(2).textContent = (lunaPolar.length / lunaDist).toString
        }
    }

    // handle other bodies
    bodies.filter(body => body.name.getOrElse("") != "Sol" && body.name.getOrElse("") != "Luna").
      foreach(body => {
        val row = table.rows.namedItem("system_table_" + body.name.get).asInstanceOf[HTMLTableRowElement]
        val polar = Polar2.convert(sol.location, body.location)
        row.cells.item(1).textContent = polar.angle.toString
        row.cells.item(2).textContent = (polar.length / au).toString
      })
  }
}
