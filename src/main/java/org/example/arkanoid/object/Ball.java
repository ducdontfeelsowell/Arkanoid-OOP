package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image; // Thêm import này
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;

public class Ball extends MoveAbleObject {
    private double speed;
    private double offset;
    private double xCenter;
    private double yCenter;
    private double radius;

    private List<TrailSegment> trail;
    private static final int MAX_TRAIL_LENGTH = 15;
    private static final double TRAIL_DECAY_RATE = 0.05;

    // --- THÊM MỚI: Biến để lưu ảnh vệt ---
    private Image trailImage;
    // --- THÊM MỚI: Biến để lưu ảnh quả bóng ---
    private Image ballImage;

    public Ball() {
        super(
                Constants.DEFAULT_BALL_POSITION_X,
                Constants.DEFAULT_BALL_POSITION_Y,
                Constants.DEFAULT_BALL_SIZE,
                Constants.DEFAULT_BALL_SIZE,
                Constants.DEFAULT_BALL_DX,
                Constants.DEFAULT_BALL_DY
        );

        this.xCenter = Constants.DEFAULT_BALL_POSITION_X + Constants.DEFAULT_BALL_SIZE / 2;
        this.yCenter = Constants.DEFAULT_BALL_POSITION_Y + Constants.DEFAULT_BALL_SIZE / 2;

        this.radius = Constants.DEFAULT_BALL_SIZE/2;

        this.speed = Constants.DEFAULT_BALL_SPEED;
        this.offset = Constants.DEFAULT_BALL_OFFSET;
        this.trail = new ArrayList<>();

        updateVelocity();

        try {
            trailImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_TRAIL_2));
            ballImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_BALL_1));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho vệt hoặc bóng!");
            trailImage = null; // Đặt là null nếu không tải được
            ballImage = null; // Đặt là null nếu không tải được
        }
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

        trail.add(0, new TrailSegment(getX(), getY()));
        if (trail.size() > MAX_TRAIL_LENGTH) {
            trail.remove(trail.size() - 1);
        }

        if (x <= 340) {
            x = 340;
            reverseX();
        }
        if (x + width >= Constants.SCREEN_WIDTH - 290) {
            x = Constants.SCREEN_WIDTH - width - 290;
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

    private static class TrailSegment {
        double x, y;
        public TrailSegment(double x, double y) { this.x = x; this.y = y; }
    }
}
