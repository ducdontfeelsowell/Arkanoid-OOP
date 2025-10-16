package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image; // Thêm import này
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;

public class Ball extends MoveAbleObject {
    private double speed;
    private int directionX;
    private int directionY;

    private List<TrailSegment> trail;
    private static final int MAX_TRAIL_LENGTH = 15;
    private static final double TRAIL_DECAY_RATE = 0.05;

    // --- THÊM MỚI: Biến để lưu ảnh vệt ---
    private Image trailImage;
    // --- THÊM MỚI: Biến để lưu ảnh quả bóng ---
    private Image ballImage;

    public Ball(double x, double y, double width, double height,
                double dx, double dy,
                double speed, int directionX, int directionY) {

        super(x, y, width, height, dx, dy);

        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.trail = new ArrayList<>();

        updateVelocity();

        // --- THÊM MỚI: Tải ảnh cho vệt ---
        try {
            // Thay đổi đường dẫn đến tệp ảnh vệt của bạn
            trailImage = new Image(getClass().getResourceAsStream("/Images/ball/ball.png"));
            // --- THÊM MỚI: Tải ảnh cho quả bóng ---
            ballImage = new Image(getClass().getResourceAsStream("/Images/ball/ball.png"));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho vệt hoặc bóng!");
            trailImage = null; // Đặt là null nếu không tải được
            ballImage = null; // Đặt là null nếu không tải được
        }
    }

    private void updateVelocity() {
        double diagonalSpeed = speed / Math.sqrt(2);
        dx = diagonalSpeed * directionX;
        dy = diagonalSpeed * directionY;
    }

    @Override
    public void move() {
        x += dx;
        y += dy;

        trail.add(0, new TrailSegment(getX(), getY()));
        if (trail.size() > MAX_TRAIL_LENGTH) {
            trail.remove(trail.size() - 1);
        }

        if (x <= 0) {
            x = 0;
            reverseX();
        }
        if (x + width >= Constants.SCREEN_WIDTH) {
            x = Constants.SCREEN_WIDTH - width;
            reverseX();
        }
        if (y <= 0) {
            y = 0;
            reverseY();
        }
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        // --- SỬA ĐỔI: Vẽ hiệu ứng vệt bằng ảnh ---
        if (trailImage != null) {
            for (int i = trail.size() - 1; i >= 0; i--) {
                //tốc độ render trail
                if(i % 1 != 0){
                    continue;
                }
                TrailSegment segment = trail.get(i);
                double opacity = 1.0 - (double) i / MAX_TRAIL_LENGTH;
                opacity = Math.max(0, opacity - TRAIL_DECAY_RATE);

                // Đặt độ mờ trước khi vẽ ảnh
                gc.setGlobalAlpha(opacity);

                // Vẽ ảnh tại vị trí của vệt
                gc.drawImage(trailImage,
                        segment.x + getWidth() * 0, segment.y + getHeight() * 0,
                        getWidth() * 1, getHeight() * 1);
            }
            // Reset lại độ mờ để không ảnh hưởng đến các đối tượng khác
            gc.setGlobalAlpha(1.0);
        }

        // Vẽ bóng đổ (shadow)
        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillOval(getX() + 2, getY() + 2, getWidth(), getHeight());

        // --- SỬA ĐỔI: Vẽ quả bóng bằng ảnh ---
        if (ballImage != null) {
            // Dùng drawImage để vẽ ảnh quả bóng
            gc.drawImage(ballImage, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dự phòng: Nếu không tải được ảnh, vẽ hình tròn màu đỏ
            gc.setFill(Color.rgb(255, 100, 100));
            gc.fillOval(getX(), getY(), getWidth(), getHeight());
        }

    }

    public void clearTrail() {
        if (trail != null) {
            trail.clear();
        }
    }

    // ... (Các getter, setter và phương thức khác không đổi) ...
    public void reverseX() {
        directionX = -directionX;
        updateVelocity();
    }
    public void reverseY() {
        directionY = -directionY;
        updateVelocity();
    }
    public boolean isCollidingWith(GameObject other) {
        return x < other.getX() + other.getWidth() &&
                x + width > other.getX() &&
                y < other.getY() + other.getHeight() &&
                y + height > other.getY();
    }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; updateVelocity(); }
    public int getDirectionX() { return directionX; }
    public void setDirectionX(int directionX) { this.directionX = directionX; updateVelocity(); }
    public int getDirectionY() { return directionY; }
    public void setDirectionY(int directionY) { this.directionY = directionY; updateVelocity(); }

    private static class TrailSegment {
        double x, y;
        public TrailSegment(double x, double y) { this.x = x; this.y = y; }
    }
}