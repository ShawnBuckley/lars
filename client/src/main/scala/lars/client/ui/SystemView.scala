package lars.client.ui

import lars.client.celestial.{CelestialBody, System}
import lars.core.math.Vec2
import lars.core.physics.units.Length
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.collection.mutable

class SystemView(elementId: String) {
  private var mouseClicked = false
  private var system: System = _
  private var systemName: String = _
  private var sprites: mutable.HashMap[String, String] = _
  private var locations = new mutable.HashMap[String, Vec2]()
  private var highlights = new mutable.HashSet[String]()

  private val canvas: HTMLCanvasElement = document.getElementById(elementId).asInstanceOf[HTMLCanvasElement]
  private val context: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  // viewport state
  private var zoom = 25.0
  private var size = Vec2(canvas.width, canvas.height)
  private var viewport = -size/2
  private var prevLocation = Vec2.addIdent

  private val au: Int = Length.Km.au.toInt
  private var pixelDistance = ((au * 2) / size.x) * zoom

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
      viewport -= current - prevLocation
      prevLocation = current
      render()
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

    // focal point location on the canvas
    val focalCoords = Vec2(event.pageX, event.pageY)

    // in game location of the mouse
    val focalLocation = unproject(Vec2(event.pageX, event.pageY))

    pixelDistance = (au/2 / size.x) * zoom

    viewport = viewportLocation(focalLocation) - focalCoords
    event.preventDefault()
  }

  private def clear(): Unit = {
    locations.clear()
    context.clearRect(0, 0, size.x, size.y)
  }

  def render(): Unit = {
    clear()
    render(system, Vec2.addIdent)
  }

  private def render(system: System, offset: Vec2): Unit = {
     system.bodies.foreach({
       case system: System =>
        render(system, offset + system.location)
       case body: CelestialBody =>
         sprites.get(body.name.getOrElse("")) match {
           case None =>
           case Some(color: String) =>
             render(body, offset, color)
         }
     })
  }

  def render(body: CelestialBody, offset: Vec2, color: String): Unit = {
    locations.put(body.name.get, offset + body.location)

    val coords = project(body.location + offset)
    val bodySize = {
      val logSize = Math.log((body.size.km / pixelDistance) * 1000)
      if(logSize > 1)
        logSize.toInt
      else
        1
    }
    if(coords.x >= 0 - bodySize && coords.x <= size.x + bodySize &&
      coords.y >= 0  - bodySize && coords.y <= size.y + bodySize) {
      if(highlights.contains(body.name.get)) {
        context.beginPath()
        context.arc(coords.x, coords.y, bodySize * 2, 0, 2 * Math.PI)
        context.lineWidth = 1
        context.strokeStyle = "white"
        context.stroke()
      }
      context.beginPath()
      context.arc(coords.x, coords.y, bodySize, 0, 2 * Math.PI)
      context.fillStyle = color
      context.fill()
      context.lineWidth = 1
    }
  }

  /**
    * Unprojects from viewport coordinates to game location.
    * @param coords viewport coordinates
    * @return game location
    */
  def unproject(coords: Vec2): Vec2 = {
    (viewport + coords) * pixelDistance
  }

  /**
    * Projects from game location to viewport coordinates.
    * @param location game location
    * @return viewport coordinates
    */
  def project(location: Vec2): Vec2 = {
    viewportLocation(location) - viewport
  }

  def viewportLocation(location: Vec2): Vec2 = {
    location / pixelDistance
  }

  def center: Vec2 = {
    viewport + (size / 2)
  }

  private def center(coords: Vec2): Unit = {
    viewport = coords - (size / 2)
    render()
  }

  def focus(name: String): Unit = {
    locations.get(name) match {
      case Some(location: Vec2) =>
        println(s"Focusing on $name")
        center(viewportLocation(location))
      case None =>
        println(s"Focus error: no body named $name")
    }
  }

  def highlight(name: String): Unit = {
    locations.get(name) match {
      case Some(location: Vec2) =>
        println(s"Hightlighting $name")
        highlights += name
      case None =>
        println(s"Highlight error: no body named $name")
    }
  }

  def unhighlight(name: String): Unit = {
    highlights -= name
  }

  def update(systemName: String, system: System): Unit = {
    clear()
    this.systemName = systemName
    this.system = system
    this.sprites = PlanetSprite.system.get(systemName).get
    render()
  }
}
