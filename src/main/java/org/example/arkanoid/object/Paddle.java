package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

public class Paddle extends MoveAbleObject {

    private double speed;

    public Paddle(double x, double y, double width, double height,
                  double dx, double dy, double speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
    }

    @Override
    public void move() {
        // Paddle chỉ di chuyển theo trục X
        x += dx;

        // Giới hạn trong khung màn hình
        if (x < 0) {
            x = 0;
        } else if (x + width > Constants.SCREEN_WIDTH) {
            x = Constants.SCREEN_WIDTH - width;
        }
    }

    public void moveLeft() {
        dx = -speed;
        move();
    }

    public void moveRight() {
        dx = speed;
        move();
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(100, 150, 255));
        gc.fillRoundRect(getX(), getY(),
                getWidth(), getHeight(), 10, 10);

        // Highlight
        gc.setFill(Color.rgb(150, 200, 255, 0.5));
        gc.fillRoundRect(getX(), getY(),
                getWidth(), getHeight() / 3, 10, 10);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
