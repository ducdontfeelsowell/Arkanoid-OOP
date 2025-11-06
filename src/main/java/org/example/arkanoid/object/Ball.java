package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager; // THÊM MỚI

import java.util.ArrayList;
import java.util.List;

public class Ball extends MoveAbleObject {
    private double speed;
    private double offset;
    private double xCenter;
    private double yCenter;
    private double radius;
    private boolean sideHit = false;

    private List<TrailSegment> trail;
    private static final int MAX_TRAIL_LENGTH = 15;
    private static final double TRAIL_DECAY_RATE = 0.05;

    // --- THÊM MỚI: Biến để lưu ảnh vệt ---
    private Image trailImage;
    // --- THÊM MỚI: Biến để lưu ảnh quả bóng ---
    private Image ballImage;

    // --- THÊM MỚI: Biến cho logic nhấp nháy của bóng ---
    private boolean showWhileFlashing_ball = true;
    private long lastFlashToggleTime_ball = 0;
    private final long FLASH_INTERVAL = 100_000_000L; // 100ms
    // --- KẾT THÚC THÊM MỚI ---

    public Ball(double positionX, double positionY, double offset, double dx, double dy) {
        super(
                positionX,
                positionY,
                Constants.DEFAULT_BALL_SIZE,
                Constants.DEFAULT_BALL_SIZE,
                dx,
                dy
        );

        this.xCenter = positionX + Constants.DEFAULT_BALL_SIZE / 2;
        this.yCenter = positionY + Constants.DEFAULT_BALL_SIZE / 2;

        this.radius = Constants.DEFAULT_BALL_SIZE/2;

        this.speed = Constants.CURRENT_BALL_SPEED;
        this.offset = offset;
        this.trail = new ArrayList<>();

        updateVelocity();

        try {
            trailImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_TRAIL_2));
            ballImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_TRAIL_2));
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

        if (x <= Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
            reverseX();
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_WALL_HIT); // ÂM THANH TƯỜNG
        }
        if (x + width >= Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
            reverseX();
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_WALL_HIT); // ÂM THANH TƯỜNG
        }

        // --- Va chạm với tường trên ---
        if (y <= 0) {
            y = 0;
            reverseY();
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_WALL_HIT); // ÂM THANH TƯỜNG
        }
    }

    // Không xử lý rơi xuống dưới ở đây, trả về kiểm tra để GameManager xử lý
    public boolean isOffScreen() {
        return y + Constants.DEFAULT_BALL_SIZE > Constants.SCREEN_HEIGHT; // Ra khỏi màn hình phía trên
    }

    @Override
    public void update() {
        move();
    }

    // --- SỬA ĐỔI: Tách logic vẽ ra ---
    private void draw(GraphicsContext gc) {
        // --- SỬA ĐỔI: Vẽ hiệu ứng vệt bằng ảnh ---
        if (trailImage != null) {
            for (int i = trail.size() - 1; i >= 0; i--) {
                //tốc độ render trail
                if(i % 2 != 0){
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

    public boolean getSideHit() {
        return sideHit;
    }

    public void setSideHit(boolean sideHit) {
        this.sideHit = sideHit;
    }

    private static class TrailSegment {
        double x, y;
        public TrailSegment(double x, double y) { this.x = x; this.y = y; }
    }
}
