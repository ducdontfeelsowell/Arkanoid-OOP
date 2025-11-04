package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager; // THÊM MỚI

import java.util.Objects;

public class Paddle extends MoveAbleObject {

    private double speed;

    private Image image0;
    private Image image1;
    private Image image2;

    private Image shooterImage0;

    private int currentImageIndex = 0;
    private long lastToggleTime = 0;
    private final long TOGGLE_INTERVAL = 200_000_000; // 200ms

    private boolean isShooter = false;
    private double shooterEndTime = 0;

    // --- Logic nhấp nháy và bất tử ---
    private boolean isInvincible = false;
    private long invincibilityEndTime = 0;
    private boolean showWhileFlashing = true;
    private long lastFlashToggleTime = 0;
    private final long FLASH_INTERVAL = 100_000_000L; // 100ms
    // --- Kết thúc ---

    public Paddle() {
        super(
                Constants.DEFAULT_PADDLE_POSITION_X,
                Constants.DEFAULT_PADDLE_POSITION_Y,
                Constants.DEFAULT_PADDLE_WIDTH,
                Constants.DEFAULT_PADDLE_HEIGHT,
                Constants.DEFAULT_PADDLE_DX,
                Constants.DEFAULT_PADDLE_DY);

        this.speed = Constants.DEFAULT_PADDLE_SPEED;

        try {
            image0 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_0)));
            image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_1)));
            image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_2)));

            shooterImage0 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_SHOOTER_PADDLE)));

        } catch (Exception e) {
            System.err.println("Không thể tải ảnh cho paddle!");
            e.printStackTrace();
            image0 = null;
            image1 = null;
            image2 = null;
            shooterImage0 = null;
        }
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

        // --- SỬA ĐỔI: Thêm âm thanh khi hết hiệu ứng súng ---
        if (isShooter && System.nanoTime() > shooterEndTime) {
            isShooter = false;
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
        }
        // --- KẾT THÚC SỬA ĐỔI ---

        // Cập nhật trạng thái bất tử
        if (isInvincible && System.nanoTime() > invincibilityEndTime) {
            isInvincible = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // Xử lý nhấp nháy
        if (isInvincible) {
            long now = System.nanoTime();
            if (now - lastFlashToggleTime > FLASH_INTERVAL) {
                showWhileFlashing = !showWhileFlashing;
                lastFlashToggleTime = now;
            }
            if (!showWhileFlashing) {
                return;
            }
        }

        long currentTime = System.nanoTime();
        if (currentTime - lastToggleTime > TOGGLE_INTERVAL) {
            currentImageIndex = (currentImageIndex + 1) % 3;
            lastToggleTime = currentTime;
        }

        Image currentImage;
        if (isShooter) {
            switch (currentImageIndex) {
                case 0: currentImage = shooterImage0; break;
                default: currentImage = shooterImage0; break;
            }
        } else {
            switch (currentImageIndex) {
                case 0: currentImage = image0; break;
                case 1: currentImage = image1; break;
                case 2: currentImage = image2; break;
                default: currentImage = image0; break;
            }
        }

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            gc.setFill(Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void activateShooter() {
        this.isShooter = true;
        this.shooterEndTime = System.nanoTime() + Constants.DEFAULT_SHOOTER_DURATION;
    }

    // --- CÁC PHƯƠNG THỨC MỚI CHO LOGIC BẤT TỬ ---
    public void activateInvincibility(long durationNano) {
        this.isInvincible = true;
        this.invincibilityEndTime = System.nanoTime() + durationNano;
    }

    public void stopInvincibility() {
        this.isInvincible = false;
    }

    public boolean isInvincible() {
        return isInvincible;
    }
    // --- KẾT THÚC PHƯƠNG THỨC MỚI ---

    public void resetState() {
        this.isShooter = false;
        this.isInvincible = false; // Cập nhật resetState
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
        this.x = Constants.DEFAULT_PADDLE_POSITION_X;
    }

    public boolean isShooter() {
        return isShooter;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}