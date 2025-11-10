package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ProgressManager; // <-- THÊM MỚI
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.game.BallManager;

import java.util.Objects;

public class RenderUI {

    private static long lastFrameTime = System.nanoTime();
    private static int frameCount = 0;
    private static int currentFPS = 0;
    private static long fpsUpdateTimer = 0;

    private static Image heartImage;
    private static final int MAX_LIVES_TO_SHOW = 6;
    private static final int HEARTS_PER_ROW = 3;
    private static final double HEART_SIZE = 30;
    private static final double HEART_PADDING = 5;

    // --- Biến cho Icon Timer ---
    private static Image shooterIcon;
    private static Image safetyIcon;
    private static Image expandIcon;
    private static Image shrinkIcon;
    private static Image slowIcon;

    private static final double ICON_SIZE = 30;
    private static final double ICON_TEXT_PADDING = 10;

    // Tọa độ Y bắt đầu vẽ timer/counter (dưới SCORE)
    // SỬA ĐỔI: Tăng giá trị Y để chừa không gian cho COIN
    private static final double TIMER_START_Y = 110;
    private static final double TIMER_LINE_HEIGHT = 40; // Khoảng cách giữa các timer
    private static final double TIMER_START_X = 20; // Thẳng hàng với SCORE


    // Tọa độ X bắt đầu cho cột UI (Tăng không gian từ 150 lên 220)
    private static final double UI_RIGHT_COLUMN_X = Constants.SCREEN_WIDTH - 220;

    // Tọa độ Y cho chữ "LIVES:"
    private static final double LIVES_TEXT_Y = 40;

    // Tọa độ X bắt đầu vẽ tim (Bên phải chữ "LIVES:")
    private static final double HEART_START_X = UI_RIGHT_COLUMN_X + 75;

    // Tọa độ Y cho hàng tim ĐẦU TIÊN (Căn giữa với chữ "LIVES:")
    private static final double HEART_ROW_1_Y = 15;

    // Tọa độ Y cho hàng tim THỨ HAI (Bên dưới hàng 1)
    private static final double HEART_ROW_2_Y = HEART_ROW_1_Y + HEART_SIZE + HEART_PADDING;

    static {
        try {
            heartImage = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_EXTRA_LIFE)
            ));
        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải ảnh trái tim (extra_heart.png)!");
            e.printStackTrace();
            heartImage = null;
        }

        // --- Tải icon cho timer ---
        try {
            // Shooter Icon
            shooterIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SHOOTER_ITEM_ANIM[0])
            ));
            // Safety Net Icon
            safetyIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SAFETY_ANIM[0])
            ));
            // Expand Icon
            expandIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_EXPAND_ANIM[0])
            ));
            // Shrink Icon
            shrinkIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SHRINK_ANIM[0])
            ));
            // Slow Icon
            slowIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SLOW_ANIM[0])
            ));

        } catch (Exception e) {
            System.err.println("Lỗi: Không thể tải ảnh timer icons!");
            shooterIcon = null;
            safetyIcon = null;
            expandIcon = null;
            shrinkIcon = null;
            slowIcon = null;
        }
    }

    public static void render(int score, int lives, GameManager gm, Paddle paddle, BallManager ballManager, GraphicsContext gc) {
        // Tính FPS (giữ nguyên - OK vì FPS nên chạy kể cả khi pause)
        calculateFPS();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Score (trái) - (giữ nguyên)
        gc.fillText("SCORE: " + score, 20, 40);

        // --- THÊM MỚI: VẼ SỐ COIN ---
        // Đặt font giống SCORE và vẽ ở Y=70 (dưới SCORE 30px)
        gc.fillText("COIN: " + ProgressManager.currentCoins, 20, 70);
        // --- KẾT THÚC THÊM MỚI ---

        // --- Vẽ "LIVES:" ---
        gc.fillText("LIVES:", UI_RIGHT_COLUMN_X, LIVES_TEXT_Y);

        // --- Vẽ trái tim (bên phải chữ LIVES:) ---
        if (heartImage != null) {
            int livesToDraw = Math.min(lives, MAX_LIVES_TO_SHOW);

            for (int i = 0; i < livesToDraw; i++) {
                int row = i / HEARTS_PER_ROW; // Hàng 0 hoặc 1
                int col = i % HEARTS_PER_ROW; // Cột 0, 1, hoặc 2

                // Căn X theo vị trí bắt đầu của tim
                double drawX = HEART_START_X + col * (HEART_SIZE + HEART_PADDING);

                // Căn Y theo hàng 1 hoặc hàng 2
                double drawY = (row == 0) ? HEART_ROW_1_Y : HEART_ROW_2_Y;

                gc.drawImage(heartImage, drawX, drawY, HEART_SIZE, HEART_SIZE);
            }
        } else {
            // Dự phòng: Nếu ảnh lỗi, vẽ số bên cạnh chữ LIVES:
            gc.fillText("" + lives, HEART_START_X, LIVES_TEXT_Y);
        }

        // --- Vị trí FPS ---

        // Tính Y cho FPS (nằm dưới 2 hàng tim)
        double fpsY = HEART_ROW_2_Y + HEART_SIZE + 20;

        // Vẽ FPS, căn X thẳng hàng với chữ "LIVES:"
        gc.fillText("FPS: " + currentFPS, UI_RIGHT_COLUMN_X, fpsY);


        // --- Vẽ Timer và Ball Counter (Dưới SCORE) ---

        // Đặt lại Font
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        double currentTimerY = TIMER_START_Y;
        // long now = System.nanoTime(); // <-- XÓA DÒNG NÀY

        // 1. Kiểm tra và vẽ Ball Counter (nếu có > 1 bóng)
        int ballCount = ballManager.getBallCount();
        if (ballCount > 1) {
            Image ballIcon = ballManager.getBallIcon();
            String counterText = ": " + ballCount;

            // Vẽ Icon
            if (ballIcon != null) {
                gc.drawImage(ballIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            // Vẽ Text
            gc.fillText(counterText, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;
        }


        // 2. Kiểm tra và vẽ Shooter Timer
        if (paddle.isShooter()) {
            // long remainingNano = (long)paddle.getShooterEndTime() - now; // <-- SỬA DÒNG NÀY
            long remainingNano = (long)paddle.getShooterRemainingTime(); // <-- THAY BẰNG DÒNG NÀY
            if (remainingNano > 0) {
                double remainingSeconds = remainingNano / 1_000_000_000.0;
                String formattedTime = String.format(": %.2fs", remainingSeconds);

                // Vẽ Icon
                if (shooterIcon != null) {
                    gc.drawImage(shooterIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
                }
                // Vẽ Text
                gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

                currentTimerY += TIMER_LINE_HEIGHT;
            }
        }

        // 3. Kiểm tra và vẽ Size Timer (Expand/Shrink)
        // if (paddle.getSizeEndTime() != 0) { // <-- SỬA DÒNG NÀY
        if (paddle.getSizeRemainingTime() > 0) { // <-- THAY BẰNG DÒNG NÀY
            // long remainingNano = (long)paddle.getSizeEndTime() - now; // <-- SỬA DÒNG NÀY
            long remainingNano = (long)paddle.getSizeRemainingTime(); // <-- THAY BẰNG DÒNG NÀY

            // if (remainingNano > 0) { // <-- Có thể xóa check này vì đã check ở trên
            double remainingSeconds = remainingNano / 1_000_000_000.0;
            String formattedTime = String.format(": %.2fs", remainingSeconds);

            Image iconToDraw = null;
            // Xác định icon dựa trên kích thước hiện tại so với kích thước mặc định
            if (paddle.getWidth() > Constants.DEFAULT_PADDLE_WIDTH) {
                iconToDraw = expandIcon;
            } else if (paddle.getWidth() < Constants.DEFAULT_PADDLE_WIDTH) {
                iconToDraw = shrinkIcon;
            }

            // Vẽ Icon
            if (iconToDraw != null) {
                gc.drawImage(iconToDraw, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            // Vẽ Text
            gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;
            // }
        }

        // 4. Kiểm tra và vẽ Speed Timer (Slow/Fast)
        // if (paddle.getSpeedEndTime() != 0) { // <-- SỬA DÒNG NÀY
        if (paddle.getSpeedRemainingTime() > 0) { // <-- THAY BẰNG DÒNG NÀY
            // long remainingNano = (long)paddle.getSpeedEndTime() - now; // <-- SỬA DÒNG NÀY
            long remainingNano = (long)paddle.getSpeedRemainingTime(); // <-- THAY BẰNG DÒNG NÀY

            // if (remainingNano > 0) { // <-- Có thể xóa check này
            double remainingSeconds = remainingNano / 1_000_000_000.0;
            String formattedTime = String.format(": %.2fs", remainingSeconds);

            Image iconToDraw = null;

            // Xác định icon Slow
            // Sửa: Dùng hằng số (hoặc biến originalSpeed nếu có) để so sánh
            if (paddle.getSpeed() < Constants.CURRENT_PADDLE_SPEED) {
                iconToDraw = slowIcon;
            }
            // Note: Bạn có thể thêm logic cho "Fast Speed" nếu có item đó

            // Vẽ Icon
            if (iconToDraw != null) {
                gc.drawImage(iconToDraw, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            // Vẽ Text
            gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;
            // }
        }


        // 5. Kiểm tra và vẽ Safety Net Timer
        if (gm.isSafetyNetActive()) {
            // long remainingNano = (long)gm.getSafetyNetEndTime() - now; // <-- SỬA DÒNG NÀY
            long remainingNano = (long)gm.getSafetyNetRemainingTime(); // <-- THAY BẰNG DÒNG NÀY
            if (remainingNano > 0) {
                double remainingSeconds = remainingNano / 1_000_000_000.0;
                String formattedTime = String.format(": %.2fs", remainingSeconds);

                // Vẽ Icon
                if (safetyIcon != null) {
                    gc.drawImage(safetyIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
                }
                // Vẽ Text
                gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);
            }
        }
    }

    /**
     * Tính toán FPS mỗi giây (giữ nguyên)
     */
    private static void calculateFPS() {
        long currentTime = System.nanoTime();
        frameCount++;
        fpsUpdateTimer += currentTime - lastFrameTime;
        lastFrameTime = currentTime;

        if (fpsUpdateTimer >= 1_000_000_000L) {
            currentFPS = frameCount;
            frameCount = 0;
            fpsUpdateTimer = 0;
        }
    }
}cd