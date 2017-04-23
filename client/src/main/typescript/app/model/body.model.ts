import { Vec2 } from "./vec2.model";
import { Mass } from "./mass.model";
import { Length } from "./length.model";

export class Body {
    name: string;
    mass: Mass;
    location: Vec2;
    velocity: Vec2;
    size: Length;

    constructor(
            name: string,
            mass: Mass,
            location: Vec2,
            velocity: Vec2,
            size: Length) {
        this.name = name;
        this.mass = mass;
        this.location = location;
        this.velocity = velocity;
        this.size = size;
    }
}