/// <reference path="typings/jquery/jquery.d.ts" />
/// <reference path="typings/jquery-mousewheel.d.ts" />
/// <reference path="typings/socket.io-client/socket.io-client.d.ts" />
/// <reference path="typings/lib.es6.d.ts" />
var PlanetSprite = (function () {
    function PlanetSprite(color, size) {
        this.color = color;
        this.size = size;
    }
    return PlanetSprite;
}());
var planet_sprites = new Map();
planet_sprites.set('Sol', new PlanetSprite('yellow', 5));
planet_sprites.set('Luna', new PlanetSprite('white', 1));
planet_sprites.set('Mercury', new PlanetSprite('gray', 1));
planet_sprites.set('Venus', new PlanetSprite('red', 2));
planet_sprites.set('Earth', new PlanetSprite('blue', 2));
planet_sprites.set('Mars', new PlanetSprite('red', 1));
planet_sprites.set('Ceres', new PlanetSprite('white', 1));
planet_sprites.set('Jupiter', new PlanetSprite('brown', 4));
planet_sprites.set('Saturn', new PlanetSprite('orange', 4));
planet_sprites.set('Uranus', new PlanetSprite('white', 3));
planet_sprites.set('Neptune', new PlanetSprite('blue', 3));
var Vector2 = (function () {
    function Vector2() {
        this.x = 0;
        this.y = 0;
    }
    return Vector2;
}());
var Planet = (function () {
    function Planet() {
    }
    return Planet;
}());
var Polar2 = (function () {
    function Polar2(angle, length) {
        this.angle = angle;
        this.length = length;
    }
    Polar2.convert = function (origin, location) {
        return new Polar2(Math.atan2(location.y, location.x), Math.sqrt(Math.pow(location.x - origin.x, 2) + Math.pow(location.y - origin.y, 2)));
    };
    return Polar2;
}());
var view;
$(document).ready(function () {
    createPlanetTable('planets_table');
    view = new SpaceView('planets_view', 30);
});
function createPlanetTable(id) {
    var table = document.getElementById(id);
    var button = document.getElementById('playpause');
    button.onclick = playpause;
    $.get('rest/system/Sol', function (planets) {
        $.each(planets, function (i, planet) {
            var row = table.insertRow(i);
            row.id = 'planets_table_' + i;
            var name = row.insertCell(0);
            name.id = row.id + '_name';
            var x = row.insertCell(1);
            x.id = row.id + '_x';
            x.className = 'planet-location';
            var y = row.insertCell(2);
            y.id = row.id + '_y';
            y.className = 'planet-location';
            var button = document.createElement('button');
            button.className = 'planet-button';
            button.appendChild(document.createTextNode(planet.name));
            button.onclick = function () { view.goToPlanet(i); };
            name.appendChild(button);
        });
    });
}
function playpause() {
    $.post('debug', null, function () { });
}
function updatePlanetsTable(planets) {
    // update sun location
    var sun = planets[0].location;
    $('#planets_table_0_x').text(sun.x / 1.496e+8);
    $('#planets_table_0_y').text(sun.y / 1.496e+8);
    var earth = planets[4];
    var moon = Polar2.convert(earth, planets[1].location);
    $('#planets_table_1_x').text(moon.angle);
    $('#planets_table_1_y').text(moon.length / 1.496e+8);
    for (var i = 2; i < planets.length; i++) {
        var polar = Polar2.convert(sun, planets[i].location);
        $('#planets_table_' + i + '_x').text(polar.angle);
        $('#planets_table_' + i + '_y').text(polar.length / 1.496e+8);
    }
}
var SpaceView = (function () {
    function SpaceView(canvasId, fps) {
        var _this = this;
        this.size = new Vector2();
        this.halfSize = new Vector2();
        this.zoom = 10;
        // 25 au in km
        this.dist = 3.73994677e8;
        this.mouseDown = false;
        this.planets = [];
        this.viewport = new Vector2();
        this.prevLocation = new Vector2();
        this.toPixels = function (location) {
            var result = new Vector2;
            result.x = location.x / _this.pixelDistance;
            result.y = location.y / _this.pixelDistance;
            return result;
        };
        this.toLocation = function (pixels) {
            var result = new Vector2;
            result.x = pixels.x * _this.pixelDistance;
            result.y = pixels.y * _this.pixelDistance;
            return result;
        };
        this.updatePlanets = function () {
            _this.socket.emit('planets', null);
            setTimeout(_this.updatePlanets, _this.rate);
        };
        this.getCenter = function () {
            var result = new Vector2;
            result.x = _this.viewport.x - _this.halfSize.x;
            result.y = _this.viewport.y - _this.halfSize.y;
            return result;
        };
        this.setCenter = function (loc) {
            _this.viewport.x = loc.x + _this.halfSize.x;
            _this.viewport.y = loc.y + _this.halfSize.y;
        };
        this.goToPlanet = function (id) {
            console.log('go to: ' + _this.planets[id].name);
            _this.setCenter(_this.toPixels(_this.planets[id].location));
        };
        this.renderPlanets = function () {
            _this.context.clearRect(0, 0, _this.size.x, _this.size.y);
            for (var i = 0; i < _this.planets.length; i++) {
                _this.context.beginPath();
                var planet = _this.planets[i];
                var sprite = planet_sprites.get(planet.name);
                var loc = _this.toPixels(planet.location);
                loc.x += _this.viewport.x;
                loc.y += _this.viewport.y;
                _this.context.arc(loc.x, loc.y, sprite.size, 0, 2 * Math.PI, false);
                _this.context.fillStyle = sprite.color;
                _this.context.fill();
                _this.context.lineWidth = 1;
            }
            setTimeout(_this.renderPlanets, _this.rate);
        };
        this.rate = (1 / fps) * 1000;
        this.canvas = document.getElementById(canvasId);
        this.context = this.canvas.getContext('2d');
        this.size.x = this.canvas.offsetWidth;
        this.size.y = this.canvas.offsetHeight;
        this.halfSize.x = this.size.x / 2;
        this.halfSize.y = this.size.y / 2;
        this.pixelDistance = ((this.dist * 2) / this.size.x) * this.zoom;
        $('#' + canvasId).css('background-color', 'rgba(0, 0, 0, 0.7)')
            .mousedown(function (event) {
            _this.mouseDown = true;
            _this.prevLocation.x = event.pageX;
            _this.prevLocation.y = event.pageY;
        })
            .on('mouseup blur', function () {
            _this.mouseDown = false;
        })
            .mousemove(function (event) {
            if (_this.mouseDown == true) {
                _this.viewport.x += (event.pageX - _this.prevLocation.x);
                _this.viewport.y += (event.pageY - _this.prevLocation.y);
                _this.prevLocation.x = event.pageX;
                _this.prevLocation.y = event.pageY;
            }
        })
            .on('mousewheel', function (event) {
            var doZoom = function (newZoom) {
                var center = _this.toLocation(_this.getCenter());
                _this.zoom = newZoom;
                _this.pixelDistance = ((_this.dist * 2) / _this.size.x) * newZoom;
                _this.setCenter(_this.toPixels(center));
            };
            var calc = event.deltaY;
            if (_this.zoom - calc <= 0) {
                doZoom(1);
            }
            else if (_this.zoom + calc >= 100) {
                doZoom(99);
            }
            else {
                doZoom(_this.zoom -= calc);
            }
        });
        this.socket = io('http://localhost:9092');
        this.socket.on('planets', function (msg) {
            _this.planets = JSON.parse(msg);
            updatePlanetsTable(_this.planets);
        });
        this.updatePlanets();
        this.renderPlanets();
    }
    return SpaceView;
}());
//# sourceMappingURL=index.js.map