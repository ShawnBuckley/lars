var planet_sprites = []
planet_sprites['Sol'] = {
    'color': 'yellow',
    'size': 5
}
planet_sprites['Mercury'] = {
    'color': 'gray',
    'size': 1
}
planet_sprites['Venus'] = {
    'color': 'orange',
    'size': 2
}
planet_sprites['Earth'] = {
    'color': 'blue',
    'size': 2
}
planet_sprites['Mars'] = {
    'color': 'red',
    'size': 1
}
planet_sprites['Jupiter'] = {
    'color': 'brown',
    'size': 4
}
planet_sprites['Saturn'] = {
    'color': 'orange',
    'size': 3
}
planet_sprites['Uranus'] = {
    'color': 'white',
    'size': 3
}
planet_sprites['Neptune'] = {
    'color': 'blue',
    'size': 3
}

function SpaceView(canvasId, fps) {
    var self = {}
    var rate = (1 / fps) * 1000

    var canvas = document.getElementById(canvasId)
    var context = canvas.getContext("2d");

    var canvasSize = {}
    canvasSize.x = canvas.offsetWidth
    canvasSize.y = canvas.offsetHeight

    var halfCanvasSize = {}
    halfCanvasSize.x = canvasSize.x / 2
    halfCanvasSize.y = canvasSize.y / 2

    var zoom = 10

    // 25 au in km
    var dist = 3.73994677e8
    var pixelDistance = ((dist * 2) / canvasSize.x) * zoom

    var mouseDown = false
    var planets = []

    var viewport = {}
    viewport.x = halfCanvasSize.x
    viewport.y = halfCanvasSize.y

    var previousLocation = {}
    previousLocation.x = viewport.x
    previousLocation.y = viewport.y

    ////////////////////////

    $('#' + canvasId).css('background-color', 'rgba(0, 0, 0, 0.7)')
    .mousedown(function(event) {
        mouseDown = true
        previousLocation.x = event.pageX
        previousLocation.y = event.pageY
    })
    .on('mouseup blur', function() {
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
    .mousewheel(function(event) {
        console.log(event)
        function doZoom(newZoom) {
            var center = toLocation(getCenter())
            zoom = newZoom
            pixelDistance = ((dist * 2) / canvasSize.x) * newZoom
            setCenter(toPixels(center))
        }

        var calc = event.deltaY
        if(zoom - calc <= 0) {
            doZoom(1)
        } else if(zoom + calc >= 100) {
            doZoom(99)
        } else {
            doZoom(zoom -= calc)
        }
    });

    ////////////////////////

    var socket = io('http://localhost:9092')
    socket.on('planets', function(msg) {
        planets = JSON.parse(msg)
    })

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

    function updatePlanets() {
        socket.emit('planets', null)
        setTimeout(updatePlanets, rate)
    }

    function getCenter() {
        var result = {}
        result.x = viewport.x - halfCanvasSize.x
        result.y = viewport.y - halfCanvasSize.y
        return result
    }

    function setCenter(loc) {
        viewport.x = loc.x + halfCanvasSize.x
        viewport.y = loc.y + halfCanvasSize.y
    }

    function renderPlanets() {
        context.clearRect(0, 0, canvas.width, canvas.height);
        for(var i=0; i<planets.length; i++) {
            context.beginPath();
            var planet = planets[i]
            var sprite = planet_sprites[planet.name]
            var loc = toPixels(planet.location)
            loc.x += viewport.x
            loc.y += viewport.y
            context.arc(loc.x, loc.y, sprite.size, 0, 2 * Math.PI, false);
            context.fillStyle = sprite.color;
            context.fill();
            context.lineWidth = 1;
        }
        setTimeout(renderPlanets, rate)
    }

    updatePlanets()
    renderPlanets()

    return self
}