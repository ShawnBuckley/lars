import { AfterViewInit, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { System } from "../../model/system.model";
import { Vec2 } from "../../model/vec2.model";
import { Body } from "../../model/body.model";

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
    private au: number;

    private mouseDown: boolean;


    // Angular stuff

    ngOnInit(): void {
        this.width = 800;
        this.height = 640;

        this.zoom = 25.0;

        this.size = new Vec2(this.width, this.height);
        this.viewport = new Vec2(-this.size.x / 2, -this.size.y / 2);
        this.prevLocation = new Vec2(0,0);

        this.au = 1.49598e8;

        this.pixelDistance = ((this.au * 2) / this.size.x) * this.zoom;

        this.mouseDown = false;
    }

    ngAfterViewInit(): void {
        let canvas = this.canvas.nativeElement;
        canvas.style.backgroundColor = "rgba(0, 0, 0, 0.7)";
        this.context = canvas.getContext("2d");
    }

    ngOnChanges(changes: SimpleChanges): void {
        if(!this.system || !this.context) return;
        this.viewport = new Vec2(-this.size.x / 2, -this.size.y / 2);
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
            console.log(this.prevLocation);
            this.viewport = new Vec2(
                this.viewport.x - (event.pageX - this.prevLocation.x),
                this.viewport.y - (event.pageY - this.prevLocation.y));
            this.prevLocation = new Vec2(event.pageX, event.pageY);
            this.update();
        }
    }

    center(): Vec2 {
        return new Vec2(
            this.viewport.x + (this.size.x / 2),
            this.viewport.y + (this.size.y / 2));
    }

    viewportLocation(location: Vec2): Vec2 {
        return new Vec2(
            location.x / this.pixelDistance,
            location.y / this.pixelDistance);
    }

    project(location: Vec2): Vec2 {
        let viewportLocation = this.viewportLocation(location);
        return new Vec2(
            viewportLocation.x - this.viewport.x,
            viewportLocation.y - this.viewport.y);
    }

    unproject(coords: Vec2): Vec2 {
        return new Vec2(
            (this.viewport.x + coords.x) * this.pixelDistance,
            (this.viewport.y + coords.y) * this.pixelDistance);
    }

    clear(): void {
        this.context.clearRect(0, 0, this.width, this.height);
    }

    renderBody(body: Body, color: string, offset: Vec2): void {
        // calculate non linear inverse perspective scaled body size
        let bodySize = Math.log((body.size.km / this.pixelDistance) * 1000);
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
                this.renderBody(body, 'white', offset)
        });
    }

    update(): void {
        this.clear();
        this.render(this.system, new Vec2(0,0));
    }
}
