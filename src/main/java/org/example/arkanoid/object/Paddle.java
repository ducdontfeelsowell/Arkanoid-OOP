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

    // --- TỪ BẢN 1: normalFrames là NON-STATIC ---
    // Mỗi paddle sẽ tự tải skin animation của riêng nó
    private List<Image> normalFrames;

    // --- TỪ BẢN 3: Các hiệu ứng này là static (chung) ---
    private static List<Image> staticShooterFrames;
    private static List<Image> staticMaterializeFrames;

    // Tải các hiệu ứng (Shooter, Bất tử) một lần duy nhất
    static {
        staticShooterFrames = new ArrayList<>();
        staticMaterializeFrames = new ArrayList<>();

        try {
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
            staticShooterFrames.clear();
            staticMaterializeFrames.clear();
        }
    }

    // --- TỪ BẢN 3: Toàn bộ logic timer dựa trên deltaTime ---
    private int currentImageIndex = 0;
    private final long TOGGLE_INTERVAL = 200_000_000; // 200ms
    private long toggleAccumulator = 0; // Bộ đếm animation

    private boolean isShooter = false;
    private double shooterRemainingTime = 0; // Dùng deltaTime

    // --- Logic bất tử ---
    private boolean isInvincible = false;
    private double invincibilityRemainingTime = 0; // Dùng deltaTime

    private int materializeFrameIndex = 0;
    private final long MATERIALIZE_INTERVAL = 40_000_000L; // 40ms/frame
    private long materializeAccumulator = 0; // Dùng deltaTime
    // --- Kết thúc ---

    private double sizeRemainingTime = 0; // Dùng deltaTime
    private double originalWidth = Constants.DEFAULT_PADDLE_WIDTH;

    private double speedRemainingTime = 0; // Dùng deltaTime
    private double originalSpeed = Constants.CURRENT_PADDLE_SPEED;


    // --- TỪ BẢN 1: Constructor này là chính xác ---
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

        // Khởi tạo và tải skin động
        this.normalFrames = new ArrayList<>();
        loadEquippedSkin();
    }

    // --- TỪ BẢN 1: Phương thức này giữ nguyên ---
    /**
     * Phương thức trợ giúp mới để tải skin chính xác khi Paddle được tạo
     */
    private void loadEquippedSkin() {
        try {
            // Lấy skin đã được Main.java tải từ ProgressManager
            String equippedSkinPath = Constants.CURRENTLY_EQUIPPED_PADDLE;

            if (equippedSkinPath != null && equippedSkinPath.equals(Constants.PATH_TO_PADDLE_1)) {
                // --- TRƯỜNG HỢP 1: Trang bị PADDLE_SKIN_2 (dùng paddle1.png) ---
                // Tải animation 2-frame theo yêu cầu của bạn
                this.normalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(Constants.PATH_TO_PADDLE_1))));
                this.normalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(Constants.PATH_TO_PADDLE_2))));
                System.out.println("Đã tải Skin Paddle 2-frame (Paddle 1 & 2).");

            } else {
                // --- TRƯỜNG HỢP 2: Trang bị MẶC ĐỊNH (paddle0.png) hoặc skin không xác định ---
                // Tải animation 3-frame nhấp nháy
                for (String path : Constants.PATH_TO_PADDLE_PULSATE_ANIM) {
                    this.normalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
                }
                System.out.println("Đã tải Skin Paddle Mặc định (3-frame Pulsate).");
            }

        } catch (Exception e) {
            System.err.println("Lỗi nghiêm trọng khi tải skin Paddle! Dùng skin mặc định.");
            e.printStackTrace();
            // Dự phòng: Tải animation 3-frame mặc định nếu có lỗi
            this.normalFrames.clear();
            try {
                for (String path : Constants.PATH_TO_PADDLE_PULSATE_ANIM) {
                    this.normalFrames.add(new Image(Objects.requireNonNull(Paddle.class.getResourceAsStream(path))));
                }
            } catch (Exception e2) {
                System.err.println("Không thể tải cả skin dự phòng!");
            }
        }
    }

    // --- TỪ BẢN 3: Logic di chuyển này là chính xác ---
    @Override
    public void move() {
        x += dx * speed; // Sử dụng speed * dx

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

    // --- TỪ BẢN 3: update(long deltaTime) là logic chính ---
    public void update(long deltaTime) {
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
            // SỬA LỖI: Dùng 'normalFrames' (non-static)
            List<Image> frames = isShooter ? staticShooterFrames : normalFrames;
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

    // --- TỪ BẢN 3: update() rỗng để override ---
    /**
     * Phương thức này bắt buộc phải có do kế thừa từ GameObject/MoveAbleObject.
     */
    @Override
    public void update() {
        // Để trống (Logic đã chuyển sang update(long deltaTime))
    }

    // --- TỪ BẢN 3: render() dựa trên logic đã tính toán ---
    @Override
    public void render(GraphicsContext gc) {
        Image currentImage = null;

        // Xử lý animation bất tử/xuất hiện
        if (isInvincible) {
            // Chỉ hiển thị animation materialize trong khi nó chưa chạy xong
            if (materializeFrameIndex < staticMaterializeFrames.size()) {
                currentImage = staticMaterializeFrames.get(materializeFrameIndex);
            } else {
                // Nếu animation materialize đã chạy xong, vẽ paddle thường/shooter
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

    // --- TỪ BẢN 3: Lấy frame đã được tính toán (SỬA LỖI) ---
    /**
     * Phương thức mới để lấy frame animation hiện tại (Đã được update() tính toán)
     */
    private Image getNormalOrShooterFrame() {
        // SỬA LỖI: Dùng 'normalFrames' (non-static)
        List<Image> frames = isShooter ? staticShooterFrames : normalFrames;

        if (frames == null || frames.isEmpty()) return null;

        if (currentImageIndex >= frames.size()) {
            currentImageIndex = 0;
        }

        return frames.get(currentImageIndex);
    }

    // --- TOÀN BỘ PHẦN CÒN LẠI LÀ TỪ BẢN 3 (Đã chính xác) ---

    public void activateShooter() {
        this.isShooter = true;
        this.shooterRemainingTime = Constants.DEFAULT_SHOOTER_DURATION;
    }

    private void deactivateShooter() {
        this.isShooter = false;
        this.shooterRemainingTime = 0;
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
    }

    // --- CÁC PHƯƠNG THỨC KÍCH THƯỚC ---
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

    // --- CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---
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

    // --- CÁC PHƯƠNG THỨC BẤT TỬ ---
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

    // --- KẾT THÚC CÁC PHƯƠNG THỨC BẤT TỬ ---

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