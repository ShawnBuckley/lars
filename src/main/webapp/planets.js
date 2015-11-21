var canvasSize = 1000
var halfCanvasSize = canvasSize / 2
var zoom = 10
// 25 au in km
var dist = 3.73994677e8
var pixelDistance = ((dist * 2) / canvasSize) * zoom
console.log(pixelDistance)

var canvas
var context

var mouseDown = false

var viewport = {}
viewport.x = halfCanvasSize
viewport.y = halfCanvasSize

var previousLocation = {}
previousLocation.x = viewport.x
previousLocation.y = viewport.y

var planets = []

var planet_sprites = []
planet_sprites[0] = {
    'color': 'yellow',
    'size': 5
}
planet_sprites[1] = {
    'color': 'gray',
    'size': 1
}
planet_sprites[2] = {
    'color': 'orange',
    'size': 2
}
planet_sprites[3] = {
    'color': 'blue',
    'size': 2
}
planet_sprites[4] = {
    'color': 'red',
    'size': 1
}
planet_sprites[5] = {
    'color': 'brown',
    'size': 4
}
planet_sprites[6] = {
    'color': 'orange',
    'size': 3
}
planet_sprites[7] = {
    'color': 'white',
    'size': 3
}
planet_sprites[8] = {
    'color': 'blue',
    'size': 3
}

function loaded() {
    var jqPlanets = $('#planet')
    $('#planets').css('background-color', 'rgba(0, 0, 0, 0.7)')
    .mousedown(function(event) {
        mouseDown = true
        previousLocation.x = event.pageX
        previousLocation.y = event.pageY
    })
    .mouseup(function() {
        mouseDown = false
    })
    .blur(function() {
        mouseDown = false
    })
    .mousemove(function(event) {
        if(mouseDown == true) {
            viewport.x += (event.pageX - previousLocation.x)
            viewport.y += (event.pageY - previousLocation.y)
            previousLocation.x = event.pageX
            previousLocation.y = event.pageY
        }
    })
    $('#planets').on('mousewheel unmousewheel', function(event) {
        var calc = event.deltaY
        if(zoom - calc <= 0) {
            zoom = 1
            pixelDistance = ((dist * 2) / canvasSize) * zoom
        } else if(zoom + calc >= 100) {
            zoom = 99
            pixelDistance = ((dist * 2) / canvasSize) * zoom
        } else {
            zoom -= calc
            pixelDistance = ((dist * 2) / canvasSize) * zoom
        }
    });

    canvas = document.getElementById("planets");
    context = canvas.getContext("2d");

    updatePlanets()
    renderPlanets()
}

function updatePlanets() {
    $.get("planets", function(data) {
        planets = data
    })
    setTimeout(updatePlanets, 10)
}

function renderPlanets() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    for(var i=0; i<planets.length; i++) {
        context.beginPath();
        var loc = toPixels(planets[i])
        loc.x += viewport.x
        loc.y += viewport.y
        context.arc(loc.x, loc.y, planet_sprites[i].size, 0, 2 * Math.PI, false);
        context.fillStyle = planet_sprites[i].color;
        context.fill();
        context.lineWidth = 1;
    }
    setTimeout(renderPlanets, 10)
}

function toPixels(location) {
    var result = {}
    result.x = location.x / pixelDistance
    result.y = location.y / pixelDistance
    return result
}

function toLocation(pixels) {
    var result = {}
    result.x = pixels.x * pixelDistance
    result.y = pixels.y * pixelDistance
    return result
}