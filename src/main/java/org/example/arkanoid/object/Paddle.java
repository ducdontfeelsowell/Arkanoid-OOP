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
            for (String path : Constants.PATH_TO_PADDLE_PULSATE_ANIM) {
                staticNormalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
            }

            for (String path : Constants.PATH_TO_SHOOTER_PULSATE_ANIM) {
                staticShooterFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
            }

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
    private final long TOGGLE_INTERVAL = 200_000_000;
    private long toggleAccumulator = 0;

    private boolean isShooter = false;
    private double shooterRemainingTime = 0;

    private boolean isInvincible = false;
    private double invincibilityRemainingTime = 0;

    private int materializeFrameIndex = 0;
    private final long MATERIALIZE_INTERVAL = 40_000_000L;
    private long materializeAccumulator = 0;

    private double sizeRemainingTime = 0;
    private double originalWidth = Constants.DEFAULT_PADDLE_WIDTH;


    private double speedRemainingTime = 0;
    private double originalSpeed = Constants.CURRENT_PADDLE_SPEED;


    public Paddle() {
        super(
                Constants.DEFAULT_PADDLE_POSITION_X,
                Constants.DEFAULT_PADDLE_POSITION_Y,
                Constants.DEFAULT_PADDLE_WIDTH,
                Constants.DEFAULT_PADDLE_HEIGHT,
                Constants.DEFAULT_PADDLE_DX,
                Constants.DEFAULT_PADDLE_DY);

        this.speed = Constants.CURRENT_PADDLE_SPEED;
        this.originalSpeed = Constants.CURRENT_PADDLE_SPEED;
    }

    @Override
    public void move() {
        x += dx * speed;

        if (x < Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
        } else if (x + width > Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
        }
    }

    public void moveLeft() {
        dx = -1;
    }

    public void moveRight() {
        dx = +1;
    }

    public void stopMove() {
        dx = 0;
    }



    public void update(long deltaTime) {
        move();


        if (isShooter) {
            shooterRemainingTime -= deltaTime;
            if (shooterRemainingTime <= 0) {
                deactivateShooter();
            }
        }

        if (isInvincible) {
            invincibilityRemainingTime -= deltaTime;
            if (invincibilityRemainingTime <= 0) {
                isInvincible = false;
                invincibilityRemainingTime = 0;
            }
        }

        if (sizeRemainingTime > 0) {
            sizeRemainingTime -= deltaTime;
            if (sizeRemainingTime <= 0) {
                resetSize();
                sizeRemainingTime = 0;
            }
        }

        if (speedRemainingTime > 0) {
            speedRemainingTime -= deltaTime;
            if (speedRemainingTime <= 0) {
                resetSpeed();
                speedRemainingTime = 0;
            }
        }


        toggleAccumulator += deltaTime;
        if (toggleAccumulator > TOGGLE_INTERVAL) {
            List<Image> frames = isShooter ? staticShooterFrames : staticNormalFrames;
            if (!frames.isEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % frames.size();
            }
            toggleAccumulator -= TOGGLE_INTERVAL;
        }

        if (isInvincible) {
            materializeAccumulator += deltaTime;
            if (materializeAccumulator > MATERIALIZE_INTERVAL) {
                if (materializeFrameIndex < staticMaterializeFrames.size()) {
                    materializeFrameIndex++;
                }
                materializeAccumulator -= MATERIALIZE_INTERVAL;
            }
        } else {
            materializeFrameIndex = 0;
            materializeAccumulator = 0;
        }
    }

    /**
     * Phương thức này bắt buộc phải có do kế thừa từ GameObject/MoveAbleObject.
     * Tuy nhiên, toàn bộ logic game đã được chuyển sang update(long deltaTime)
     * và được gọi trực tiếp từ GameManager.
     */
    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        Image currentImage = null;

        if (isInvincible) {


            if (materializeFrameIndex < staticMaterializeFrames.size()) {
                currentImage = staticMaterializeFrames.get(materializeFrameIndex);
            } else {
                currentImage = getNormalOrShooterFrame();
            }
        } else {
            // Vẽ paddle thường hoặc shooter
            currentImage = getNormalOrShooterFrame();
        }

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dự phòng
            gc.setFill(Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    /**
     * Phương thức mới để lấy frame animation hiện tại (Đã được update() tính toán)
     */
    private Image getNormalOrShooterFrame() {
        List<Image> frames = isShooter ? staticShooterFrames : staticNormalFrames;

        if (frames.isEmpty()) return null;


        if (currentImageIndex >= frames.size()) {
            currentImageIndex = 0;
        }

        return frames.get(currentImageIndex);
    }

    public void activateShooter() {
        this.isShooter = true;
        this.shooterRemainingTime = Constants.DEFAULT_SHOOTER_DURATION;
    }

    private void deactivateShooter() {
        this.isShooter = false;
        this.shooterRemainingTime = 0;
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
    }

    public void setSizeWithTimeout(double newWidth) {
        if (sizeRemainingTime <= 0) {
            this.originalWidth = this.width;
        }

        this.width = newWidth;


        this.sizeRemainingTime = Constants.DEFAULT_SIZE_DURATION;
    }

    public void cancelSizeTimeout() {
        resetSize();
        this.sizeRemainingTime = 0;
    }

    private void resetSize() {
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
    }

    public double getSizeRemainingTime() {
        return sizeRemainingTime;
    }

    public void setSpeedWithTimeout(double newSpeed) {
        if (speedRemainingTime <= 0) {
            this.originalSpeed = this.speed;
        }

        this.speed = newSpeed;


        this.speedRemainingTime = Constants.DEFAULT_SPEED_DURATION;
    }

    public void cancelSpeedTimeout() {
        resetSpeed();
        this.speedRemainingTime = 0;
    }

    private void resetSpeed() {
        this.speed = Constants.CURRENT_PADDLE_SPEED;
    }

    public double getSpeedRemainingTime() {
        return speedRemainingTime;
    }


    // CÁC PHƯƠNG THỨC BẤT TỬ
    public void activateInvincibility(long durationNano) {
        this.isInvincible = true;

        this.invincibilityRemainingTime = durationNano;

        this.materializeFrameIndex = 0;
        this.materializeAccumulator = 0;
    }

    public double getInvincibilityRemainingTime() {
        return invincibilityRemainingTime;
    }

    public void stopInvincibility() {
        this.isInvincible = false;
        this.materializeFrameIndex = staticMaterializeFrames.size();
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void resetState() {
        this.isShooter = false;
        this.isInvincible = false;

        this.sizeRemainingTime = 0;
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
        this.x = Constants.DEFAULT_PADDLE_POSITION_X;

        this.speed = Constants.CURRENT_PADDLE_SPEED;
        this.speedRemainingTime = 0;

        this.shooterRemainingTime = 0;
        this.invincibilityRemainingTime = 0;

        this.currentImageIndex = 0;
        this.materializeFrameIndex = 0;
        this.toggleAccumulator = 0;
        this.materializeAccumulator = 0;
    }

    public boolean isShooter() {
        return isShooter;
    }

    public double getShooterRemainingTime() {
        return shooterRemainingTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}