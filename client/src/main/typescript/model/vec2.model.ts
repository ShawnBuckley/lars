export class Vec2 {
    x: number;
    y: number;

    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }

    add(other: Vec2): Vec2 {
        return new Vec2(
            this.x + other.x,
            this.y + other.y
        );
    }

    sub(other: Vec2): Vec2 {
        return new Vec2(
            this.x - other.x,
            this.y - other.y
        );

    }

    mul(other: number): Vec2 {
        return new Vec2(
            this.x * other,
            this.y * other
        );

    }

    div(other: number): Vec2 {
        return new Vec2(
            this.x / other,
            this.y / other
        );

    }
}