package lars

import lars.client.Body
import lars.core.math.Vec2
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLCanvasElement

class SystemView(elementId: String) {
  var mouseDown = false
  var bodies: Array[Body] = _

  val canvas: HTMLCanvasElement = document.getElementById(elementId).asInstanceOf[HTMLCanvasElement]
  val context: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  // viewport state
  private var zoom = 25.0
  private var size = Vec2(canvas.width, canvas.height)
  private var viewport = Vec2.addIdent
  private var prevLocation = Vec2.addIdent

  // 25 au in km
  private val dist = 3.73994677e8
  private var pixelDistance = ((dist * 2) / size.x) * zoom

  // setup the canvas
  canvas.style.backgroundColor = "rgba(0, 0, 0, 0.7)"
  canvas.addEventListener("mousedown", (event: dom.MouseEvent) => {
    mouseDown = true
    prevLocation = Vec2(event.pageX, event.pageY)
  })

  canvas.addEventListener("mouseup", mouseUp)
  canvas.addEventListener("blur", mouseUp)
  canvas.addEventListener("mousemove", (event: dom.MouseEvent) => {
    if(mouseDown) {
      val current = Vec2(event.pageX, event.pageY)
      viewport += current - prevLocation
      prevLocation = current
    }
  })
  canvas.addEventListener("mousewheel", mouseZoom)
  canvas.addEventListener("DOMMouseScroll", mouseZoom)

  private def mouseUp(event: dom.MouseEvent): Unit = {
    mouseDown = false
  }

  private def mouseZoom(event: dom.WheelEvent): Unit = {
    val newZoom = zoom + event.deltaY
    if(newZoom >= 0 && newZoom <= 100) {
      zoom = newZoom
      pixelDistance = ((dist * 2) / size.x) * newZoom
      center(toPixels(toLocation(center)))
    }
    event.preventDefault()
  }

  private def clear(): Unit = {
    context.clearRect(0, 0, size.x, size.y)
  }

  private def render(): Unit = {
    context.clearRect(0, 0, canvas.width, canvas.height)
    bodies.foreach(body => {
      PlanetSprite.sol.get(body.name.getOrElse("")) match {
        case None =>
        case Some(sprite: PlanetSprite) =>
          context.beginPath()
          val loc = toPixels(body.location) + viewport
          context.arc(loc.x, loc.y, sprite.size, 0, 2 * Math.PI, anticlockwise = false)
          context.fillStyle = sprite.color
          context.fill()
          context.lineWidth = 1
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
      case Some(body: Body) =>
        println("Focusing on " + name)
        center(-toPixels(body.location))
    }
  }

  def update(bodies: Array[Body]): Unit = {
    this.bodies = bodies
    render()
  }
}
