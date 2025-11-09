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

    // --- SỬA ĐỔI: Chuyển 'normalFrames' thành non-static ---
    // Mỗi paddle sẽ tự tải skin animation của riêng nó
    private List<Image> normalFrames;

    // Giữ lại các frame static cho các hiệu ứng chung
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


    private int currentImageIndex = 0;
    private long lastToggleTime = 0;
    // SỬA ĐỔI: Giảm thời gian animation một chút để skin 2 frame nhìn rõ hơn
    private final long TOGGLE_INTERVAL = 150_000_000; // 150ms

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

    private double speedEndTime = 0;
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

        // --- SỬA LỖI LOGIC: Tải skin động dựa trên Constants ---
        this.normalFrames = new ArrayList<>();
        loadEquippedSkin();
    }

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

        if (isShooter && (double)System.nanoTime() > shooterEndTime) {
            isShooter = false;
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_LOAD);
        }

        // Cập nhật trạng thái bất tử
        if (isInvincible && (double)System.nanoTime() > invincibilityEndTime) {
            isInvincible = false;
        }

        // Logic Reset kích thước
        if (sizeEndTime != 0 && (double)System.nanoTime() > sizeEndTime) {
            this.width = originalWidth;
            sizeEndTime = 0;
        }

        // Logic Reset tốc độ Paddle
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
        // (Logic này giữ nguyên, nó ưu tiên đè lên skin thường)
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

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dự phòng
            gc.setFill(Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    /**
     * Phương thức này lấy frame animation hiện tại
     * (Logic này giữ nguyên, nó hoạt động chính xác)
     */
    private Image getNormalOrShooterFrame(long currentTime) {
        // --- Dùng 'this.normalFrames' (non-static) thay vì 'staticNormalFrames' ---
        // 'staticShooterFrames' vẫn đúng vì hiệu ứng shooter là chung
        List<Image> frames = isShooter ? staticShooterFrames : this.normalFrames;

        if (frames == null || frames.isEmpty()) return null;

        // Nếu danh sách chỉ có 1 frame (skin tĩnh), nó sẽ luôn trả về 0
        if (frames.size() == 1) {
            currentImageIndex = 0;
        }
        // Nếu có nhiều frame (animation), thì xoay vòng
        else if (currentTime - lastToggleTime > TOGGLE_INTERVAL) {
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
        this.shooterEndTime = (double)System.nanoTime() + Constants.DEFAULT_SHOOTER_DURATION;
    }

    // --- CÁC PHƯƠNG THỨC KÍCH THƯỚC ---
    public void setSizeWithTimeout(double newWidth) {
        if (sizeEndTime == 0) {
            this.originalWidth = Constants.DEFAULT_PADDLE_WIDTH;
        }

        this.width = newWidth;
        this.sizeEndTime = (double)System.nanoTime() + Constants.DEFAULT_SIZE_DURATION;
    }

    public void cancelSizeTimeout() {
        this.width = originalWidth;
        this.sizeEndTime = 0;
    }

    public double getSizeEndTime() {
        return sizeEndTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC KÍCH THƯỚC ---

    // --- CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---
    public void setSpeedWithTimeout(double newSpeed) {
        if (speedEndTime == 0) {
            this.originalSpeed = Constants.CURRENT_PADDLE_SPEED;
        }

        this.speed = newSpeed;
        this.speedEndTime = (double)System.nanoTime() + Constants.DEFAULT_SPEED_DURATION;
    }

    public void cancelSpeedTimeout() {
        this.speed = originalSpeed;
        this.speedEndTime = 0;
    }

    public double getSpeedEndTime() {
        return speedEndTime;
    }
    // --- KẾT THÚC CÁC PHƯƠNG THỨC TỐC ĐỘ MỚI ---

    // --- CÁC PHƯƠNG THỨC BẤT TỬ ---
    public void activateInvincibility(long durationNano) {
        this.isInvincible = true;
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
        this.speed = Constants.CURRENT_PADDLE_SPEED;
        this.speedEndTime = 0;
    }

    public boolean isShooter() {
        return isShooter;
    }

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