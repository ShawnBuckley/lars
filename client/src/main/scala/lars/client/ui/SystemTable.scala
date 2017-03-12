package lars.client.ui

import lars.client.JsonParser
import lars.client.celestial.{CelestialBody, System}
import lars.core.math.{Polar2, Vec2}
import lars.core.physics.units.Length
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.raw.{HTMLButtonElement, HTMLTableCellElement, HTMLTableElement, HTMLTableRowElement}

import scala.scalajs.js

class SystemTable(elementId: String, view: SystemView) {
  val table: HTMLTableElement = document.getElementById(elementId).asInstanceOf[HTMLTableElement]

  val au: Int = Length.Km.au.toInt
  val lunaDist: Int = 385000

  def create(system: String): Unit = {
    // create table
    val xhr = new dom.XMLHttpRequest()
    xhr.open("GET", "rest/system/" + system)
    xhr.onload = { (e: dom.Event) =>
      if(xhr.status == 200) {
        def createTable(system: System): Unit = {
          system.bodies.foreach({
            case system: System =>
              createTable(system)
            case body: CelestialBody =>
              val row = table.insertRow().asInstanceOf[HTMLTableRowElement]
              row.id = "system_table_" + body.name.get

              val nameCell = row.insertCell().asInstanceOf[HTMLTableCellElement]
              body.name match {
                case None =>
                  nameCell.textContent = "Unnamed"
                case Some(name: String) =>
                  val bodyButton = dom.document.createElement("button").asInstanceOf[HTMLButtonElement]
                  bodyButton.className = "btn btn-sm btn-default body-button"
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

        JsonParser.parseSystem(js.JSON.parse(xhr.responseText)) match {
          case None =>
          case Some(system: System) => createTable(system)
        }
      }
    }
    xhr.send()
  }

  def truncate(double: Double): Double = {
    math.floor(double * 10000) / 10000
  }

  def update(body: CelestialBody, location: Vec2): Unit = {
    val row = table.rows.namedItem("system_table_" + body.name.get).asInstanceOf[HTMLTableRowElement]
    val polar = Polar2.convert(location)
    row.cells.item(1).textContent = truncate(polar.angle).toString
    row.cells.item(2).textContent = truncate(polar.length / au).toString
  }

  def update(body: CelestialBody): Unit = {
    update(body, body.location)
  }

  def update(system: System): Unit = {
    val bodies = system.bodies
    // handle sun absolute location
    val sol = bodies.head
    val solRow = table.rows.namedItem("system_table_" + sol.name.get).asInstanceOf[HTMLTableRowElement]
    solRow.cells.item(1).textContent = truncate(sol.location.x / au).toString
    solRow.cells.item(2).textContent = truncate(sol.location.y / au).toString

    // handle the rest of the bodies
    bodies.tail.foreach({
      case system: System =>
        val primary = system.bodies.head
        update(primary, system.location)
        system.bodies.tail.foreach(body => update(body))
      case body: CelestialBody => update(body)
    })
  }
}
