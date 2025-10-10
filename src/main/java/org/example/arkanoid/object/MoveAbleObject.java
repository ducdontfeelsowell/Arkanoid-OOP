package org.example.arkanoid.object;

public abstract class MoveAbleObject extends GameObject{

    protected double dx;
    protected double dy;

    public abstract void move();

    public MoveAbleObject() {}

    public MoveAbleObject(double x, double y, double width, double height,
                                                     double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
