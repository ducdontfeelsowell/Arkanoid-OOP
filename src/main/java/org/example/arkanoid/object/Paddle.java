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
    // private long lastToggleTime = 0; // <-- XÓA
    private final long TOGGLE_INTERVAL = 200_000_000; // 200ms
    private long toggleAccumulator = 0; // <-- THÊM: Bộ đếm animation

    private boolean isShooter = false;
    // private double shooterEndTime = 0; // <-- XÓA
    private double shooterRemainingTime = 0; // <-- THÊM

    // --- Logic bất tử ---
    private boolean isInvincible = false;
    // private double invincibilityEndTime = 0; // <-- XÓA
    private double invincibilityRemainingTime = 0; // <-- THÊM

    private int materializeFrameIndex = 0;
    // private long lastMaterializeTime = 0; // <-- XÓA
    private final long MATERIALIZE_INTERVAL = 40_000_000L; // 40ms/frame
    private long materializeAccumulator = 0; // <-- THÊM
    // --- Kết thúc ---

    // private double sizeEndTime = 0; // <-- XÓA
    private double sizeRemainingTime = 0; // <-- THÊM
    private double originalWidth = Constants.DEFAULT_PADDLE_WIDTH;

    // THÊM MỚI: Logic cho tốc độ giới hạn thời gian (long -> double)
    // private double speedEndTime = 0; // <-- XÓA
    private double speedRemainingTime = 0; // <-- THÊM
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
        // Khởi tạo tốc độ ban đầu
        this.originalSpeed = Constants.CURRENT_PADDLE_SPEED;
    }

    @Override
    public void move() {
        x += dx * speed; // <-- SỬA: Sử dụng speed * dx

        if (x < Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
        } else if (x + width > Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
        }
    }

    public void moveLeft() {
        dx = -1; // Chỉ đặt hướng
    }

    public void moveRight() {
        dx = +1; // Chỉ đặt hướng
    }

    public void stopMove() {
        dx = 0;
    }


    // public void update() { // <-- SỬA
    // @Override // <-- XÓA DÒNG NÀY
    public void update(long deltaTime) { // <-- THÊM: Nhận deltaTime
        move();

        // --- LOGIC ĐẾM NGƯỢC ITEM (ĐÃ SỬA) ---

        // Cập nhật Shooter
        if (isShooter) {
            shooterRemainingTime -= deltaTime;
            if (shooterRemainingTime <= 0) {
                deactivateShooter();
            }
        }

        // Cập nhật trạng thái bất tử
        if (isInvincible) {
            invincibilityRemainingTime -= deltaTime;
            if (invincibilityRemainingTime <= 0) {
                isInvincible = false;
                invincibilityRemainingTime = 0;
            }
        }

        // Logic Reset kích thước
        if (sizeRemainingTime > 0) {
            sizeRemainingTime -= deltaTime;
            if (sizeRemainingTime <= 0) {
                resetSize(); // Sử dụng hàm reset
                sizeRemainingTime = 0;
            }
        }

        // Logic Reset tốc độ Paddle
        if (speedRemainingTime > 0) {
            speedRemainingTime -= deltaTime;
            if (speedRemainingTime <= 0) {
                resetSpeed(); // Sử dụng hàm reset
                speedRemainingTime = 0;
            }
        }

        // --- LOGIC ANIMATION (ĐÃ SỬA) ---
        // Cập nhật animation chính (pulsate)
        toggleAccumulator += deltaTime;
        if (toggleAccumulator > TOGGLE_INTERVAL) {
            List<Image> frames = isShooter ? staticShooterFrames : staticNormalFrames;
            if (!frames.isEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % frames.size();
            }
            toggleAccumulator -= TOGGLE_INTERVAL;
        }

        // Cập nhật animation materialize (bất tử)
        if (isInvincible) {
            materializeAccumulator += deltaTime;
            if (materializeAccumulator > MATERIALIZE_INTERVAL) {
                if (materializeFrameIndex < staticMaterializeFrames.size()) {
                    materializeFrameIndex++;
                }
                materializeAccumulator -= MATERIALIZE_INTERVAL;
            }
        } else {
            // Reset khi không bất tử
            materializeFrameIndex = 0;
            materializeAccumulator = 0;
        }
    }

    // THÊM PHƯƠNG THỨC NÀY ĐỂ SỬA LỖI COMPILER
    /**
     * Phương thức này bắt buộc phải có do kế thừa từ GameObject/MoveAbleObject.
     * Tuy nhiên, toàn bộ logic game đã được chuyển sang update(long deltaTime)
     * và được gọi trực tiếp từ GameManager.
     */
    @Override
    public void update() {
        // Để trống
    }

    @Override
    public void render(GraphicsContext gc) {
        // long currentTime = System.nanoTime(); // <-- XÓA: Không cần thời gian ở đây nữa
        Image currentImage = null;

        // --- Xử lý animation bất tử/xuất hiện (Logic đã được update() xử lý) ---
        if (isInvincible) {

            // Chuyển frame materialize (ĐÃ CHUYỂN LÊN UPDATE)

            // Chỉ hiển thị animation materialize trong khi nó chưa chạy xong
            if (materializeFrameIndex < staticMaterializeFrames.size()) {
                currentImage = staticMaterializeFrames.get(materializeFrameIndex);
            } else {
                // Nếu animation materialize đã chạy xong, vẽ paddle thường/shooter
                currentImage = getNormalOrShooterFrame(); // <-- XÓA Tham số
            }
        } else {
            // Vẽ paddle thường hoặc shooter
            currentImage = getNormalOrShooterFrame(); // <-- XÓA Tham số
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
     * Phương thức mới để lấy frame animation hiện tại (Đã được update() tính toán)
     */
    private Image getNormalOrShooterFrame() { // <-- XÓA Tham số
        List<Image> frames = isShooter ? staticShooterFrames : staticNormalFrames;

        if (frames.isEmpty()) return null;

        // XÓA TẤT CẢ LOGIC THỜI GIAN Ở ĐÂY

        if (currentImageIndex >= frames.size()) {
            currentImageIndex = 0;
        }

        return frames.get(currentImageIndex);
    }

    public void activateShooter() {
        this.isShooter = true;
        // SỬA ĐỔI: Đặt thời gian còn lại
        // this.shooterEndTime = (double)System.nanoTime() + Constants.DEFAULT_SHOOTER_DURATION; // <-- XÓA
        this.shooterRemainingTime = Constants.DEFAULT_SHOOTER_DURATION; // <-- THÊM
    }

    // THÊM: Hàm tắt súng
    private void deactivateShooter() {
        this.isShooter = false;
        this.shooterRemainingTime = 0;
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
    }

    // --- CÁC PHƯƠNG THỨC KÍCH THƯỚC ---
    public void setSizeWithTimeout(double newWidth) {
        if (sizeRemainingTime <= 0) { // Sửa: kiểm tra thời gian còn lại
            // Lưu lại kích thước gốc CHỈ khi bắt đầu hiệu ứng mới
            this.originalWidth = this.width; // Lưu kích thước hiện tại, không phải default
        }

        this.width = newWidth;

        // SỬA ĐỔI: Đặt thời gian còn lại
        // this.sizeEndTime = (double)System.nanoTime() + Constants.DEFAULT_SIZE_DURATION; // <-- XÓA
        this.sizeRemainingTime = Constants.DEFAULT_SIZE_DURATION; // <-- THÊM
    }

    public void cancelSizeTimeout() {
        resetSize();
        this.sizeRemainingTime = 0;
    }

    // THÊM: Hàm reset size
    private void resetSize() {
        this.width = Constants.DEFAULT_PADDLE_WIDTH; // Luôn reset về default
    }

    // SỬA ĐỔI: long -> double
    public double getSizeRemainingTime() { // <-- ĐỔI TÊN
        return sizeRemainingTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC KÍCH THƯỚC ---

    // --- CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---
    public void setSpeedWithTimeout(double newSpeed) {
        if (speedRemainingTime <= 0) { // Sửa: kiểm tra thời gian còn lại
            // Lưu tốc độ gốc CHỈ khi bắt đầu hiệu ứng mới
            this.originalSpeed = this.speed; // Lưu tốc độ hiện tại, không phải default
        }

        this.speed = newSpeed;

        // SỬA ĐỔI: Đặt thời gian còn lại
        // this.speedEndTime = (double)System.nanoTime() + Constants.DEFAULT_SPEED_DURATION; // <-- XÓA
        this.speedRemainingTime = Constants.DEFAULT_SPEED_DURATION; // <-- THÊM
    }

    public void cancelSpeedTimeout() {
        resetSpeed();
        this.speedRemainingTime = 0;
    }

    // THÊM: Hàm reset speed
    private void resetSpeed() {
        this.speed = Constants.CURRENT_PADDLE_SPEED; // Luôn reset về default (hoặc CURRENT)
    }

    // SỬA ĐỔI: long -> double
    public double getSpeedRemainingTime() { // <-- ĐỔI TÊN
        return speedRemainingTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---

    // --- CÁC PHƯƠNG THỨC BẤT TỬ ---
    public void activateInvincibility(long durationNano) {
        this.isInvincible = true;
        // SỬA ĐỔI: Đặt thời gian còn lại
        // this.invincibilityEndTime = (double)System.nanoTime() + durationNano; // <-- XÓA
        this.invincibilityRemainingTime = durationNano; // <-- THÊM

        // Reset animation
        this.materializeFrameIndex = 0;
        this.materializeAccumulator = 0;
    }

    // THÊM: Getter thời gian bất tử
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
    // --- KẾT THÚC CÁC PHƯƠNG THỨC BẤT TỬ ---

    public void resetState() {
        this.isShooter = false;
        this.isInvincible = false;

        this.sizeRemainingTime = 0; // Sửa
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
        this.x = Constants.DEFAULT_PADDLE_POSITION_X;

        // RESET timeout tốc độ
        this.speed = Constants.CURRENT_PADDLE_SPEED;
        this.speedRemainingTime = 0; // Sửa

        // Reset thời gian item
        this.shooterRemainingTime = 0; // Sửa
        this.invincibilityRemainingTime = 0; // Sửa

        // Reset animation
        this.currentImageIndex = 0;
        this.materializeFrameIndex = 0;
        this.toggleAccumulator = 0;
        this.materializeAccumulator = 0;
    }

    public boolean isShooter() {
        return isShooter;
    }

    // SỬA ĐỔI: long -> double
    public double getShooterRemainingTime() { // <-- ĐỔI TÊN
        return shooterRemainingTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}