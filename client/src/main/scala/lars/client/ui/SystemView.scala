package lars.client.ui

import lars.client.celestial.CelestialBody
import lars.core.math.Vec2
import lars.core.physics.units.Length
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLCanvasElement

class SystemView(elementId: String) {
  var mouseClicked = false
  var bodies: Seq[CelestialBody] = _

  val canvas: HTMLCanvasElement = document.getElementById(elementId).asInstanceOf[HTMLCanvasElement]
  val context: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  // viewport state
  private var zoom = 25.0
  private var size = Vec2(canvas.width, canvas.height)
  private var viewport = Vec2.addIdent
  private var prevLocation = Vec2.addIdent

  private val au: Int = Length.Km.au.toInt
  private var pixelDistance = ((au * 2) / size.x) * zoom

  center(toPixels(size/2))

  // setup the canvas
  canvas.style.backgroundColor = "rgba(0, 0, 0, 0.7)"
  canvas.addEventListener("mousedown", (e: dom.MouseEvent) => mouseDown(e))
  canvas.addEventListener("mouseup", (e: dom.MouseEvent) => mouseUp(e))
  canvas.addEventListener("blur", (e: dom.MouseEvent) => mouseUp(e))
  canvas.addEventListener("mousemove", (e: dom.MouseEvent) => mouseMove(e))
  canvas.addEventListener("mousewheel", (e: dom.WheelEvent) => mouseZoom(e))
  canvas.addEventListener("DOMMouseScroll", (e: dom.WheelEvent) => mouseZoom(e))

  private def mouseDown(event: dom.MouseEvent): Unit = {
    mouseClicked = true
    prevLocation = Vec2(event.pageX, event.pageY)
  }

  private def mouseMove(event: dom.MouseEvent): Unit = {
    if(mouseClicked) {
      val current = Vec2(event.pageX, event.pageY)
      viewport += current - prevLocation
      prevLocation = current
    }
  }

  private def mouseUp(event: dom.MouseEvent): Unit = {
    mouseClicked = false
  }

  private def mouseZoom(event: dom.WheelEvent): Unit = {
    zoom = {
      val calc = zoom + event.deltaY
      if(calc <= 1)
        1
      else if(calc >= 1000)
        1000
      else
        calc
    }
    pixelDistance = (au/2 / size.x) * zoom
    center(toPixels(toLocation(center)))
    event.preventDefault()
  }

  private def clear(): Unit = {
    context.clearRect(0, 0, size.x, size.y)
  }

  private def render(system: String): Unit = {
    context.clearRect(0, 0, canvas.width, canvas.height)
    bodies.foreach(body => {
      PlanetSprite.system.get(system) match {
        case None =>
        case Some(system) =>
          system.get(body.name.getOrElse("")) match {
            case None =>
            case Some(color: String) =>
              context.beginPath()
              val loc = toPixels(body.location) + viewport
              context.arc(loc.x, loc.y, {
                val logSize = Math.log((body.size.km / pixelDistance) * 1000)
                if(logSize > 1)
                  logSize
                else
                  1
              }, 0, 2 * Math.PI)
              context.fillStyle = color
              context.fill()
              context.lineWidth = 1
          }
      }
    })
  }

  def center: Vec2 = {
    viewport - (size / 2)
  }

  private def center(location: Vec2): Unit = {
    viewport = location + (size / 2)
  }

  private def toLocation(pixels: Vec2): Vec2 = {
    pixels * pixelDistance
  }

  private def toPixels(location: Vec2): Vec2 = {
    location / pixelDistance
  }

  def focus(name: String): Unit = {
    bodies.find(_.name.get == name) match {
      case None =>
        println("Focus error: no body named " + name + " was found")
      case Some(body: CelestialBody) =>
        println("Focusing on " + name)
        center(-toPixels(body.location))
    }
  }

  def update(system: String, bodies: Seq[CelestialBody]): Unit = {
    this.bodies = bodies
    render(system)
  }
}
