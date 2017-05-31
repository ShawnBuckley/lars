import { Vec2 } from "./vec2.model";

export class Body {
    name: string;
    mass: number;
    location: Vec2;
    velocity: Vec2;
    size: number;

    constructor(
            name: string,
            mass: number,
            location: Vec2,
            velocity: Vec2,
            size: number) {
        this.name = name;
        this.mass = mass;
        this.location = location;
        this.velocity = velocity;
        this.size = size;
    }
}