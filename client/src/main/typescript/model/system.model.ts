import { Body } from "./body.model";
import { Vec2 } from "./vec2.model";
import { Mass } from "./mass.model";
import { Length } from "./length.model";

export class System extends Body {
    bodies: System[];

    constructor(
            name: string,
            mass: Mass,
            location: Vec2,
            velocity: Vec2,
            size: Length,
            bodies: System[]) {
        super(name, mass, location, velocity, size);
        this.bodies = bodies;
    }
}