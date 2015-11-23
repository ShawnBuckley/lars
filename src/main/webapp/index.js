var view

$(document).ready(function() {
    createPlanetTable('planets_table')
    view = new SpaceView('planets_view', 30)
})

function createPlanetTable(id) {
    var table = document.getElementById(id)
    $.get('planets', function(planets) {
        $.each(planets, function(i, planet){
            console.log(planet)
            var row = table.insertRow(i)
            row.id = 'planets_table_' + i
            var name = row.insertCell(0)
            name.id = row.id + '_name'
            var x = row.insertCell(1)
            x.id = row.id + '_x'
            x.className = 'planet-location'
            var y = row.insertCell(2)
            y.id = row.id + '_y'
            y.className = 'planet-location'
            var button = document.createElement('button')
            button.className = 'planet-button'
            button.appendChild(document.createTextNode(planet.name))
            button.onclick = function() { view.goToPlanet(i) }
            name.appendChild(button)
        })
    })
}

function playpause() {
    $.post('debug', null, function(data) {
    })
}

function updatePlanetsTable(planets) {

    // update sun location
    var sun = planets[0].location
    $('#planets_table_0_x').text(sun.x / 1.496e+8)
    $('#planets_table_0_y').text(sun.y / 1.496e+8)

    for(var i=1; i<planets.length; i++) {
        var x = planets[i].location.x
        var y = planets[i].location.y
        var angle = Math.atan2(y, x)
        var length = Math.sqrt(Math.pow(x - sun.x, 2) + Math.pow(y - sun.y, 2))
        $('#planets_table_' + i + '_x').text(angle)
        $('#planets_table_' + i + '_y').text(length / 1.496e+8)
    }
}