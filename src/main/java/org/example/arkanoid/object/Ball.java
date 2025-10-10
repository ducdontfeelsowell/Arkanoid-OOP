package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

public class Ball extends MoveAbleObject {
    private double speed;
    private int directionX; // 1: chiều dương, -1: chiều âm trục X
    private int directionY; // 1: chiều dương, -1: chiều âm trục Y

    public Ball(double x, double y, double width, double height,
                double dx, double dy,
                double speed, int directionX, int directionY) {

        super(x, y, width, height, dx, dy);

        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;

        updateVelocity();
    }

    /**
     * Cập nhật vận tốc (dx, dy) dựa vào hướng và tốc độ.
     */
    private void updateVelocity() {
        double diagonalSpeed = speed / Math.sqrt(2);
        dx = diagonalSpeed * directionX;
        dy = diagonalSpeed * directionY;
    }

    @Override
    public void move() {
        x += dx;
        y += dy;

        // --- Va chạm với tường trái ---
        if (x <= 0) {
            x = 0;
            reverseX();
        }

        // --- Va chạm với tường phải ---
        if (x + width >= Constants.SCREEN_WIDTH) {
            x = Constants.SCREEN_WIDTH - width;
            reverseX();
        }

        // --- Va chạm với tường trên ---
        if (y <= 0) {
            y = 0;
            reverseY();
        }

        // Không xử lý rơi xuống dưới ở đây, để GameManager xử lý
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillOval(getX() + 2, getY() + 2,
                getWidth(), getHeight());

        // Ball
        gc.setFill(Color.rgb(255, 100, 100));
        gc.fillOval(getX(), getY(),
                getWidth(), getHeight());

        // Highlight
        gc.setFill(Color.rgb(255, 200, 200, 0.7));
        gc.fillOval(getX() + 3, getY() + 3,
                getWidth() / 3, getHeight() / 3);
    }

    /**
     * Đảo chiều theo trục X.
     */
    public void reverseX() {
        directionX = -directionX;
        updateVelocity();
    }

    /**
     * Đảo chiều theo trục Y.
     */
    public void reverseY() {
        directionY = -directionY;
        updateVelocity();
    }

    /**
     * Kiểm tra va chạm giữa hai hình chữ nhật (AABB collision).
     */
    public boolean isCollidingWith(GameObject other) {
        return x < other.getX() + other.getWidth() &&
                x + width > other.getX() &&
                y < other.getY() + other.getHeight() &&
                y + height > other.getY();
    }

    // ===== Getter & Setter =====
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        updateVelocity();
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
        updateVelocity();
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
        updateVelocity();
    }
}