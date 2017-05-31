import { AfterViewInit, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';

import { Body } from "../../../model/body.model"
import { System } from "../../../model/system.model";
import { Vec2 } from "../../../model/vec2.model";

@Component({
    selector: 'system-view',
    templateUrl: 'partial/system-view.html'
})
export class SystemViewComponent implements OnInit, OnChanges, AfterViewInit {
    @ViewChild("systemview") canvas: ElementRef;
    @Input() system: System;

    width: number;
    height: number;

    context: CanvasRenderingContext2D;

    // state
    private zoom: number;
    private viewport: Vec2;
    private prevLocation: Vec2;
    private size: Vec2;

    private pixelDistance: number;

    private mouseDown: boolean;

    // TODO - temporary
    private bodyColors: any;

    ngOnInit(): void {
        this.width = 800;
        this.height = 640;

        this.zoom = 2.0;

        this.size = new Vec2(this.width, this.height);
        this.viewport = this.size.div(-2);
        this.prevLocation = new Vec2(0,0);

        this.mouseDown = false;

        // TODO - implement this somewhere else
        this.bodyColors = {};
        this.bodyColors['Sol'] = {};
        this.bodyColors['Sol']['Sol'] = 'yellow';
        this.bodyColors['Sol']['Luna'] = 'white';
        this.bodyColors['Sol']['Mercury'] = 'gray';
        this.bodyColors['Sol']['Venus'] = 'red';
        this.bodyColors['Sol']['Earth'] = 'blue';
        this.bodyColors['Sol']['Mars'] = 'red';
        this.bodyColors['Sol']['Ceres'] = 'white';
        this.bodyColors['Sol']['Jupiter'] = 'brown';
        this.bodyColors['Sol']['Saturn'] = 'orange';
        this.bodyColors['Sol']['Uranus'] = 'white';
        this.bodyColors['Sol']['Neptune'] = 'blue';
        this.bodyColors['Sol']['Pluto'] = 'orange';
        this.bodyColors['Sol']['Charon'] = 'white';

        this.bodyColors['TRAPPIST-1'] = {};
        this.bodyColors['TRAPPIST-1']['a'] = 'red';
        this.bodyColors['TRAPPIST-1']['b'] = 'orange';
        this.bodyColors['TRAPPIST-1']['c'] = 'yellow';
        this.bodyColors['TRAPPIST-1']['d'] = 'blue';
        this.bodyColors['TRAPPIST-1']['e'] = 'blue';
        this.bodyColors['TRAPPIST-1']['f'] = 'blue';
        this.bodyColors['TRAPPIST-1']['g'] = 'green';
        this.bodyColors['TRAPPIST-1']['h'] = 'white';
    }

    ngAfterViewInit(): void {
        let canvas = this.canvas.nativeElement;
        canvas.style.backgroundColor = "rgba(0, 0, 0, 0.7)";
        this.context = canvas.getContext("2d");
    }

    ngOnChanges(changes: SimpleChanges): void {
        if(!this.system || !this.context || !changes.system) return;
        if(!changes.system.previousValue ||
                changes.system.currentValue.name != changes.system.previousValue.name) {
            this.zoom = 2.0;
            this.pixelDistance = ((this.system.size * 2) / this.size.x) * this.zoom;
        }
        this.update();
    }

    mousedown(event: MouseEvent): void {
        this.mouseDown = true;
        this.prevLocation = new Vec2(event.pageX, event.pageY);
    }

    mouseup(): void {
        this.mouseDown = false;
    }

    mousemove(event: MouseEvent): void {
        if(this.mouseDown) {
            let eventLocation = new Vec2(event.pageX, event.pageY);
            this.viewport = this.viewport.sub(eventLocation.sub(this.prevLocation));
            this.prevLocation = new Vec2(event.pageX, event.pageY);
            this.update();
        }
    }

    mousewheel(event: MouseWheelEvent): void {
        let zoom = this.zoom + event.deltaY;
        if(zoom <= 1)
            zoom = 1;
        else if(zoom >= 1000)
            zoom = 1000;
        this.zoom = zoom;


        let mouseLocation = new Vec2(event.pageX, event.pageY);
        let focalLocation = this.unproject(mouseLocation);
        this.pixelDistance = ((this.system.size * 2) / this.size.x) * this.zoom;

        let viewportLocation = this.viewportLocation(focalLocation);
        this.viewport = viewportLocation.sub(mouseLocation);
        event.preventDefault();
        this.update();
    }

    center(): Vec2 {
        return this.viewport.add(this.size.div(2));
    }

    centerOn(coords: Vec2): void {
        this.viewport.sub(coords.sub(this.size));
    }

    viewportLocation(location: Vec2): Vec2 {
        return location.div(this.pixelDistance);
    }

    project(location: Vec2): Vec2 {
        let viewportLocation = this.viewportLocation(location);
        return viewportLocation.sub(this.viewport);
    }

    unproject(coords: Vec2): Vec2 {
        return (this.viewport.add(coords)).mul(this.pixelDistance);
    }

    clear(): void {
        this.context.clearRect(0, 0, this.width, this.height);
    }

    renderBody(body: Body, color: string, offset: Vec2): void {
        // calculate non linear inverse perspective scaled body size
        let bodySize = Math.log((body.size / this.pixelDistance) * 1000);
        if(bodySize > 1)
            bodySize = Math.round(bodySize);
        else
            bodySize = 1;

        let coords = this.project(new Vec2(
            body.location.x + offset.x,
            body.location.y + offset.y
        ));

        if(coords.x >= 0 - bodySize && coords.x <= this.size.x + bodySize &&
                coords.y >= 0 - bodySize && coords.y <= this.size.y + bodySize) {
            // Handle highlighting

            // Render the body
            this.context.beginPath();
            this.context.arc(coords.x, coords.y, bodySize, 0, 2 * Math.PI);
            this.context.fillStyle = color;
            this.context.fill();
            this.context.lineWidth = 1;
        }
    }

    render(system: System, offset: Vec2): void {
        system.bodies.forEach(body => {
            if(body.bodies && body.bodies.length > 1)
                this.render(body, new Vec2(
                    body.location.x + offset.x,
                    body.location.y + offset.y));
            else
                this.renderBody(body, this.bodyColors[this.system.name][body.name], offset)
        });
    }

    focus(name: string): void {
        let body = this.system.bodies.filter(body => body.name === name)[0];
        if(body) {
            this.centerOn(this.viewportLocation(body.location));
        }
    }

    update(): void {
        this.clear();
        this.render(this.system, new Vec2(0,0));
    }
}
