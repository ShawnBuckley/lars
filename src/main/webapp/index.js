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

    function convert(origin, location) {
        var result = {}
        result.angle = Math.atan2(location.y, location.x)
        result.length = Math.sqrt(Math.pow(location.x - origin.x, 2) + Math.pow(location.y - origin.y, 2))
        return result
    }

    // update sun location
    var sun = planets[0].location
    $('#planets_table_0_x').text(sun.x / 1.496e+8)
    $('#planets_table_0_y').text(sun.y / 1.496e+8)

    var earth = planets[4]
    var moon = convert(earth, planets[1].location)
    $('#planets_table_1_x').text(moon.angle)
    $('#planets_table_1_y').text(moon.length / 1.496e+8)

    for(var i=2; i<planets.length; i++) {
        var polar = convert(sun, planets[i].location)
        $('#planets_table_' + i + '_x').text(polar.angle)
        $('#planets_table_' + i + '_y').text(polar.length / 1.496e+8)
    }
}