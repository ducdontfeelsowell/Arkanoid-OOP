package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

    private Image trailImage;
    private Image ballImage;

    // --- THÊM MỚI: Biến cho logic nhấp nháy của bóng ---
    private boolean showWhileFlashing_ball = true;
    private long lastFlashToggleTime_ball = 0;
    private final long FLASH_INTERVAL = 100_000_000L; // 100ms
    // --- KẾT THÚC THÊM MỚI ---

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
            trailImage = null;
            ballImage = null;
        }
    }

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

        if (x <= Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
            reverseX();
        }
        if (x + width >= Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
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

    // --- SỬA ĐỔI: Tách logic vẽ ra ---
    private void draw(GraphicsContext gc) {
        if (trailImage != null) {
            for (int i = trail.size() - 1; i >= 0; i--) {
                if(i % 1 != 0){
                    continue;
                }
                TrailSegment segment = trail.get(i);
                double opacity = 1.0 - (double) i / MAX_TRAIL_LENGTH;
                opacity = Math.max(0, opacity - TRAIL_DECAY_RATE);

                gc.setGlobalAlpha(opacity);

                gc.drawImage(trailImage,
                        segment.x + getWidth() * 0, segment.y + getHeight() * 0,
                        getWidth() * 1, getHeight() * 1);
            }
            gc.setGlobalAlpha(1.0);
        }

        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillOval(getX() + 2, getY() + 2, getWidth(), getHeight());

        if (ballImage != null) {
            gc.drawImage(ballImage, getX(), getY(), getWidth(), getHeight());
        } else {
            gc.setFill(Color.rgb(255, 100, 100));
            gc.fillOval(getX(), getY(), getWidth(), getHeight());
        }
    }
    // --- KẾT THÚC SỬA ĐỔI ---

    @Override
    public void render(GraphicsContext gc) {
        // Phương thức này giữ lại để tuân thủ GameObject, gọi logic vẽ cơ bản
        draw(gc);
    }

    // --- THÊM MỚI: Overload render để xử lý nhấp nháy ---
    public void render(GraphicsContext gc, boolean isInvincible) {
        if (isInvincible) {
            long now = System.nanoTime();
            if (now - lastFlashToggleTime_ball > FLASH_INTERVAL) {
                showWhileFlashing_ball = !showWhileFlashing_ball;
                lastFlashToggleTime_ball = now;
            }
            if (!showWhileFlashing_ball) {
                return; // Không vẽ bóng
            }
        }
        // Vẽ bóng bình thường
        draw(gc);
    }
    // --- KẾT THÚC THÊM MỚI ---

    public void clearTrail() {
        if (trail != null) {
            trail.clear();
        }
    }

    public void reverseX() {
        dx = -dx;
    }

    public void reverseY() {
        dy = -dy;
    }

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