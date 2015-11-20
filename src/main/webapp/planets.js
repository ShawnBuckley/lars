var canvasSize = 1000;
// 25 au in km
var dist = 3.73994677e9
var pixelDistance = (dist * 2) / canvasSize
console.log(pixelDistance)

var canvas
var context

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
    'color': 'blue',
    'size': 3
}
planet_sprites[8] = {
    'color': 'white',
    'size': 3
}

function loaded() {
    $('#planets').css('background-color', 'rgba(0, 0, 0, 0.7)');

    canvas = document.getElementById("planets");
    context = canvas.getContext("2d");

    renderPlanets()
}

function renderPlanets() {
    $.get("planets", function(data) {
        context.clearRect(0, 0, canvas.width, canvas.height);
        for(var i=0; i<data.length; i++) {
            context.beginPath();
            var x = convert(data[i]["x"])
            var y = convert(data[i]["y"])
            context.arc(x, y, planet_sprites[i].size, 0, 2 * Math.PI, false);
            context.fillStyle = planet_sprites[i].color;
            context.fill();
            context.lineWidth = 1;
        }
    });
    setTimeout(renderPlanets, 10)
}

function convert(length) {
    return (length + dist) / pixelDistance;
}