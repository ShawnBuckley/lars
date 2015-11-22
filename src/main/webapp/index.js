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
            var y = row.insertCell(2)
            y.id = row.id + '_y'
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
    for(var i=0; i<planets.length; i++) {
        $('#planets_table_' + i + '_x').text(planets[i].location.x)
        $('#planets_table_' + i + '_y').text(planets[i].location.y)
    }
}