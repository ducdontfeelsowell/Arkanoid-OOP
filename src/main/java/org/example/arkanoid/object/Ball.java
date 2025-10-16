package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

public class Ball extends MoveAbleObject {
    private double speed;
    private double offset;
    private double xCenter;
    private double yCenter;
    private double radius;

    public Ball(double x, double y, double width, double height,
                double dx, double dy,
                double speed, double offset) {

        super(x, y, width, height, dx, dy);

        this.xCenter = x + width / 2;
        this.yCenter = y + height / 2;

        this.radius = width/2;

        this.speed = speed;
        this.offset = offset;

        updateVelocity();
    }

    /**
     * Cập nhật vận tốc (dx, dy) dựa vào hướng và tốc độ. Cho va chạm paddle.
     */
    private void updateVelocity() {
        dx = speed * offset;
        dy = -Math.sqrt(Math.pow(speed, 2) - Math.pow(dx, 2));
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
     * Đảo chiều theo trục X. Cho va chạm ngoài paddle.
     */
    public void reverseX() {
        dx = -dx;
    }

    /**
     * Đảo chiều theo trục Y. Cho va chạm ngoài paddle.
     */
    public void reverseY() {
        dy = -dy;
    }

    /**
     * Kiểm tra va chạm giữa hai hình chữ nhật (AABB collision).
     */
    public boolean isCollidingWith(GameObject other) {
        return x <= other.getX() + other.getWidth() &&
                x + width >= other.getX() &&
                y <= other.getY() + other.getHeight() &&
                y + height >= other.getY();
    }

    // ===== Getter & Setter =====
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        updateVelocity();
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
        updateVelocity();
    }

    public double getxCenter() {
        return xCenter;
    }

    public double getyCenter() {
        return yCenter;
    }

    public double getRadius() {
        return radius;
    }
}
