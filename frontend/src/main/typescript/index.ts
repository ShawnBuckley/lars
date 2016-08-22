/// <reference path="typings/jquery/jquery.d.ts" />
/// <reference path="typings/jquery-mousewheel.d.ts" />
/// <reference path="typings/socket.io-client/socket.io-client.d.ts" />
/// <reference path="typings/lib.es6.d.ts" />

class PlanetSprite {
    color: string;
    size: number;

    constructor(color: string, size: number) {
        this.color = color;
        this.size = size;
    }
}

var currentSystem: String = "Sol";

var planet_sprites: Map<string, PlanetSprite> = new Map<string, PlanetSprite>();
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

planet_sprites.set('Xygon A', new PlanetSprite('yellow', 5));
planet_sprites.set('Xygon B', new PlanetSprite('yellow', 5));
planet_sprites.set('Xyferius', new PlanetSprite('red', 2));

class Vector2 {
    x: number;
    y: number;

    constructor(x: number = 0, y: number = 0) {
        this.x = x;
        this.y = y;
    }
}

class Planet {
    location: Vector2;


}

class Polar2 {
    angle: number;
    length: number;

    constructor(angle: number, length: number) {
        this.angle = angle;
        this.length = length;
    }

    static convert(origin: Vector2, location: Vector2) {
        var result = new Vector2(origin.x - location.x, origin.y - location.y);
        return new Polar2((Math.atan2(result.y, result.x)+Math.PI)*(180/Math.PI), Math.sqrt(Math.pow(result.x, 2) + Math.pow(result.y, 2)));
    }
}

var view: SpaceView;

$(document).ready(function() {
    createPlanetTable('planets_table');
    view = new SpaceView('planets_view', 30);
});

function createPlanetTable(id: string) {
    var table: HTMLTableElement = <HTMLTableElement>document.getElementById(id);
    var button: HTMLButtonElement = <HTMLButtonElement>document.getElementById('playpause');
    button.onclick=playpause;
    $.get('rest/system/' + currentSystem, function(planets) {
        $.each(planets, function(i, planet){
            var row: HTMLTableRowElement = <HTMLTableRowElement>table.insertRow(i);
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
            button.onclick = function() { view.goToPlanet(i) };
            name.appendChild(button);
        })
    });
}

function playpause() {
    $.post('rest/game/pause', null, function() {});
}

function updatePlanetsTable(planets: Array<any>) {
    // update sun location
    var sun = planets[0].location;
    $('#planets_table_0_x').text(sun.x / 1.496e+8);
    $('#planets_table_0_y').text(sun.y / 1.496e+8);

    var earth;
    for(var i=2; i<planets.length; i++) {
        if(planets[i].name == "Earth") {
            earth = planets[i];
        }
    }

    for(var i=1; i<planets.length; i++) {
        var polar;
        if(planets[i].name == "Luna") {
            polar = Polar2.convert(earth.location, planets[i].location);
            $('#planets_table_' + i + '_y').text(polar.length / 384402);
        } else {
            polar = Polar2.convert(sun, planets[i].location);
            $('#planets_table_' + i + '_y').text(polar.length / 1.496e+8);
        }
        $('#planets_table_' + i + '_x').text(polar.angle);

    }
}

class SpaceView {
    private rate: number;
    private canvas: HTMLCanvasElement;
    private size: Vector2 = new Vector2();
    private halfSize: Vector2 = new Vector2();
    private zoom: number = 10;
    private pixelDistance: number;

    // 25 au in km
    private dist: number = 3.73994677e8;

    private mouseDown: boolean = false;
    private planets: Array<any> = [];
    private viewport: Vector2 = new Vector2();
    private prevLocation: Vector2 = new Vector2();

    private socket: SocketIOClient.Socket;
    private context: CanvasRenderingContext2D;

    constructor(canvasId: string, fps: number) {
        this.rate = (1 / fps) * 1000;
        this.canvas = <HTMLCanvasElement>document.getElementById(canvasId);
        this.context = this.canvas.getContext('2d');
        this.size.x = this.canvas.offsetWidth;
        this.size.y = this.canvas.offsetHeight;
        this.halfSize.x = this.size.x / 2;
        this.halfSize.y = this.size.y / 2;
        this.pixelDistance = ((this.dist * 2) / this.size.x) * this.zoom;

        $('#' + canvasId).css('background-color', 'rgba(0, 0, 0, 0.7)')
            .mousedown((event: JQueryEventObject) => {
                this.mouseDown = true;
                this.prevLocation.x = event.pageX;
                this.prevLocation.y = event.pageY;
            })
            .on('mouseup blur', () => {
                this.mouseDown = false;
            })
            .mousemove((event: JQueryEventObject) => {
                if(this.mouseDown == true) {
                    this.viewport.x += (event.pageX - this.prevLocation.x);
                    this.viewport.y += (event.pageY - this.prevLocation.y);
                    this.prevLocation.x = event.pageX;
                    this.prevLocation.y = event.pageY;
                }
            })
            .on('mousewheel', (event: JQueryMousewheelEventObject) => {
                var doZoom = (newZoom: number) => {
                    var center = this.toLocation(this.getCenter());
                    this.zoom = newZoom;
                    this.pixelDistance = ((this.dist * 2) / this.size.x) * newZoom;
                    this.setCenter(this.toPixels(center));
                };

                var calc = event.deltaY;
                if(this.zoom - calc <= 0) {
                    doZoom(1);
                } else if(this.zoom + calc >= 100) {
                    doZoom(99);
                } else {
                    doZoom(this.zoom -= calc);
                }
            });

        this.socket = io('http://localhost:9092');
        this.socket.on('planets', (msg: any) => {
            this.planets = JSON.parse(msg);
            updatePlanetsTable(this.planets);
        });

        this.updatePlanets();
        this.renderPlanets();
    }

    toPixels = (location: Vector2) => {
        var result: Vector2 = new Vector2;
        result.x = location.x / this.pixelDistance;
        result.y = location.y / this.pixelDistance;
        return result;
    };

    toLocation = (pixels: Vector2) => {
        var result: Vector2 = new Vector2;
        result.x = pixels.x * this.pixelDistance;
        result.y = pixels.y * this.pixelDistance;
        return result;
    };

    updatePlanets = () => {
        this.socket.emit('planets', null);
    };

    getCenter = () => {
        var result: Vector2 = new Vector2;
        result.x = this.viewport.x - this.halfSize.x;
        result.y = this.viewport.y - this.halfSize.y;
        return result;
    };

    setCenter = (loc: Vector2) => {
        this.viewport.x = loc.x + this.halfSize.x;
        this.viewport.y = loc.y + this.halfSize.y;
    };

    goToPlanet = (id: number) => {
        console.log('go to: ' + this.planets[id].name);
        this.setCenter(this.toPixels(this.planets[id].location));
    };

    renderPlanets = () => {
        this.context.clearRect(0, 0, this.size.x, this.size.y);
        for(var i=0; i<this.planets.length; i++) {
            this.context.beginPath();
            var planet = this.planets[i];
            var sprite = planet_sprites.get(planet.name);
            var loc = this.toPixels(planet.location);
            loc.x += this.viewport.x;
            loc.y += this.viewport.y;
            this.context.arc(loc.x, loc.y, sprite.size, 0, 2 * Math.PI, false);
            this.context.fillStyle = sprite.color;
            this.context.fill();
            this.context.lineWidth = 1;
        }
        setTimeout(this.renderPlanets, this.rate);
    };
}