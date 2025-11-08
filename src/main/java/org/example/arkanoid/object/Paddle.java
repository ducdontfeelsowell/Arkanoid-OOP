package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Paddle extends MoveAbleObject {

    private double speed;

    private static List<Image> staticNormalFrames;
    private static List<Image> staticShooterFrames;
    private static List<Image> staticMaterializeFrames;

    static {
        staticNormalFrames = new ArrayList<>();
        staticShooterFrames = new ArrayList<>();
        staticMaterializeFrames = new ArrayList<>();

        try {
            // Ảnh Paddle thường (Pulsate)
            for (String path : Constants.PATH_TO_PADDLE_PULSATE_ANIM) {
                staticNormalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
            }

            // Ảnh Shooter Paddle (Shooter Pulsate)
            for (String path : Constants.PATH_TO_SHOOTER_PULSATE_ANIM) {
                staticShooterFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
            }

            // Animation Bất tử/Xuất hiện (Materialize)
            for (String path : Constants.PATH_TO_PADDLE_MATERIALIZE_ANIM) {
                staticMaterializeFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
            }

        } catch (Exception e) {
            System.err.println("LỖI KHỞI TẠO STATIC: Không thể tải ảnh cho paddle!");
            e.printStackTrace();
            staticNormalFrames.clear();
            staticShooterFrames.clear();
            staticMaterializeFrames.clear();
        }
    }


    private int currentImageIndex = 0;
    private long lastToggleTime = 0;
    private final long TOGGLE_INTERVAL = 200_000_000; // 200ms

    private boolean isShooter = false;
    private double shooterEndTime = 0;

    // --- Logic bất tử ---
    private boolean isInvincible = false;
    private double invincibilityEndTime = 0;

    private int materializeFrameIndex = 0;
    private long lastMaterializeTime = 0;
    private final long MATERIALIZE_INTERVAL = 40_000_000L; // 40ms/frame
    // --- Kết thúc ---

    private double sizeEndTime = 0;
    private double originalWidth = Constants.DEFAULT_PADDLE_WIDTH;

    // THÊM MỚI: Logic cho tốc độ giới hạn thời gian (long -> double)
    private double speedEndTime = 0;
    private double originalSpeed = Constants.DEFAULT_PADDLE_SPEED;


    public Paddle() {
        super(
                Constants.DEFAULT_PADDLE_POSITION_X,
                Constants.DEFAULT_PADDLE_POSITION_Y,
                Constants.DEFAULT_PADDLE_WIDTH,
                Constants.DEFAULT_PADDLE_HEIGHT,
                Constants.DEFAULT_PADDLE_DX,
                Constants.DEFAULT_PADDLE_DY);

        this.speed = Constants.CURRENT_PADDLE_SPEED;
    }

    @Override
    public void move() {
        x += dx;

        if (x < Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
        } else if (x + width > Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
        }
    }

    public void moveLeft() {
        dx = -speed;
    }

    public void moveRight() {
        dx = +speed;
    }

    public void stopMove() {
        dx = 0;
    }

    @Override
    public void update() {
        move();

        // SỬA ĐỔI: (Kiểm tra double)
        if (isShooter && (double)System.nanoTime() > shooterEndTime) {
            isShooter = false;
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
        }

        // Cập nhật trạng thái bất tử (Kiểm tra double)
        if (isInvincible && (double)System.nanoTime() > invincibilityEndTime) {
            isInvincible = false;
        }

        // Logic Reset kích thước (Kiểm tra double)
        if (sizeEndTime != 0 && (double)System.nanoTime() > sizeEndTime) {
            this.width = originalWidth;
            sizeEndTime = 0;
        }

        // Logic Reset tốc độ Paddle (Kiểm tra double)
        if (speedEndTime != 0 && (double)System.nanoTime() > speedEndTime) {
            this.speed = originalSpeed;
            speedEndTime = 0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        long currentTime = System.nanoTime();
        Image currentImage = null;

        // --- Xử lý animation bất tử/xuất hiện ---
        if (isInvincible) {

            // Chuyển frame materialize
            if (currentTime - lastMaterializeTime > MATERIALIZE_INTERVAL) {
                if (materializeFrameIndex < staticMaterializeFrames.size()) {
                    materializeFrameIndex++;
                }
                lastMaterializeTime = currentTime;
            }

            // Chỉ hiển thị animation materialize trong khi nó chưa chạy xong
            if (materializeFrameIndex < staticMaterializeFrames.size()) {
                currentImage = staticMaterializeFrames.get(materializeFrameIndex);
            } else {
                // Nếu animation materialize đã chạy xong, vẽ paddle thường/shooter
                currentImage = getNormalOrShooterFrame(currentTime);
            }
        } else {
            // Vẽ paddle thường hoặc shooter
            currentImage = getNormalOrShooterFrame(currentTime);
        }
        // --- KẾT THÚC SỬA ĐỔI ---

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dự phòng
            gc.setFill(Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    /**
     * Phương thức mới để lấy frame animation hiện tại
     */
    private Image getNormalOrShooterFrame(long currentTime) {
        List<Image> frames = isShooter ? staticShooterFrames : staticNormalFrames;

        if (frames.isEmpty()) return null;

        if (currentTime - lastToggleTime > TOGGLE_INTERVAL) {
            currentImageIndex = (currentImageIndex + 1) % frames.size();
            lastToggleTime = currentTime;
        }

        if (currentImageIndex >= frames.size()) {
            currentImageIndex = 0;
        }

        return frames.get(currentImageIndex);
    }

    public void activateShooter() {
        this.isShooter = true;
        // SỬA ĐỔI: Lưu double
        this.shooterEndTime = (double)System.nanoTime() + Constants.DEFAULT_SHOOTER_DURATION;
    }

    // --- CÁC PHƯƠNG THỨC KÍCH THƯỚC ---
    public void setSizeWithTimeout(double newWidth) {
        if (sizeEndTime == 0) {
            this.originalWidth = Constants.DEFAULT_PADDLE_WIDTH;
        }

        this.width = newWidth;

        // SỬA ĐỔI: Lưu double
        this.sizeEndTime = (double)System.nanoTime() + Constants.DEFAULT_SIZE_DURATION;
    }

    public void cancelSizeTimeout() {
        this.width = originalWidth;
        this.sizeEndTime = 0;
    }

    // SỬA ĐỔI: long -> double
    public double getSizeEndTime() {
        return sizeEndTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC KÍCH THƯỚC ---

    // --- CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---
    /**
     * Áp dụng tốc độ mới cho Paddle và đặt timeout.
     * @param newSpeed Tốc độ mới.
     */
    public void setSpeedWithTimeout(double newSpeed) {
        if (speedEndTime == 0) {
            this.originalSpeed = Constants.DEFAULT_PADDLE_SPEED;
        }

        this.speed = newSpeed;

        // SỬA ĐỔI: Lưu double
        this.speedEndTime = (double)System.nanoTime() + Constants.DEFAULT_SPEED_DURATION;
    }

    /**
     * Hủy hiệu ứng tốc độ ngay lập tức.
     */
    public void cancelSpeedTimeout() {
        this.speed = originalSpeed;
        this.speedEndTime = 0;
    }

    // SỬA ĐỔI: long -> double
    public double getSpeedEndTime() {
        return speedEndTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---

    // --- CÁC PHƯƠNG THỨC BẤT TỬ ---
    public void activateInvincibility(long durationNano) {
        this.isInvincible = true;
        // SỬA ĐỔI: Lưu double
        this.invincibilityEndTime = (double)System.nanoTime() + durationNano;

        this.materializeFrameIndex = 0;
        this.lastMaterializeTime = 0;
    }

    public void stopInvincibility() {
        this.isInvincible = false;
        this.materializeFrameIndex = staticMaterializeFrames.size();
    }

    public boolean isInvincible() {
        return isInvincible;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC BẤT TỬ ---

    public void resetState() {
        this.isShooter = false;
        this.isInvincible = false;
        this.sizeEndTime = 0;
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
        this.x = Constants.DEFAULT_PADDLE_POSITION_X;

        // RESET timeout tốc độ
        this.speed = Constants.DEFAULT_PADDLE_SPEED;
        this.speedEndTime = 0;
    }

    public boolean isShooter() {
        return isShooter;
    }

    // SỬA ĐỔI: long -> double
    public double getShooterEndTime() {
        return shooterEndTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}