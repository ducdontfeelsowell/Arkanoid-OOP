package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ProgressManager;
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

    private static Image shooterIcon;
    private static Image safetyIcon;
    private static Image expandIcon;
    private static Image shrinkIcon;
    private static Image slowIcon;

    private static final double ICON_SIZE = 30;
    private static final double ICON_TEXT_PADDING = 10;

    private static final double TIMER_START_Y = 110;
    private static final double TIMER_LINE_HEIGHT = 40;
    private static final double TIMER_START_X = 20;


    private static final double UI_RIGHT_COLUMN_X = Constants.SCREEN_WIDTH - 220;

    private static final double LIVES_TEXT_Y = 40;

    private static final double HEART_START_X = UI_RIGHT_COLUMN_X + 75;

    private static final double HEART_ROW_1_Y = 15;

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

        try {
            shooterIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SHOOTER_ITEM_ANIM[0])
            ));
            safetyIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SAFETY_ANIM[0])
            ));
            expandIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_EXPAND_ANIM[0])
            ));
            shrinkIcon = new Image(Objects.requireNonNull(
                    RenderUI.class.getResourceAsStream(Constants.PATH_TO_SHRINK_ANIM[0])
            ));
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
        calculateFPS();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        gc.fillText("SCORE: " + score, 20, 40);

        gc.fillText("COIN: " + ProgressManager.currentCoins, 20, 70);

        gc.fillText("LIVES:", UI_RIGHT_COLUMN_X, LIVES_TEXT_Y);

        if (heartImage != null) {
            int livesToDraw = Math.min(lives, MAX_LIVES_TO_SHOW);

            for (int i = 0; i < livesToDraw; i++) {
                int row = i / HEARTS_PER_ROW;
                int col = i % HEARTS_PER_ROW;

                double drawX = HEART_START_X + col * (HEART_SIZE + HEART_PADDING);

                double drawY = (row == 0) ? HEART_ROW_1_Y : HEART_ROW_2_Y;

                gc.drawImage(heartImage, drawX, drawY, HEART_SIZE, HEART_SIZE);
            }
        } else {
            gc.fillText("" + lives, HEART_START_X, LIVES_TEXT_Y);
        }


        double fpsY = HEART_ROW_2_Y + HEART_SIZE + 20;

        gc.fillText("FPS: " + currentFPS, UI_RIGHT_COLUMN_X, fpsY);



        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        double currentTimerY = TIMER_START_Y;

        int ballCount = ballManager.getBallCount();
        if (ballCount > 1) {
            Image ballIcon = ballManager.getBallIcon();
            String counterText = ": " + ballCount;

            if (ballIcon != null) {
                gc.drawImage(ballIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            gc.fillText(counterText, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;
        }


        if (paddle.isShooter()) {
            long remainingNano = (long)paddle.getShooterRemainingTime();
            if (remainingNano > 0) {
                double remainingSeconds = remainingNano / 1_000_000_000.0;
                String formattedTime = String.format(": %.2fs", remainingSeconds);

                if (shooterIcon != null) {
                    gc.drawImage(shooterIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
                }
                gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

                currentTimerY += TIMER_LINE_HEIGHT;
            }
        }


        if (paddle.getSizeRemainingTime() > 0) {
            long remainingNano = (long)paddle.getSizeRemainingTime();

            double remainingSeconds = remainingNano / 1_000_000_000.0;
            String formattedTime = String.format(": %.2fs", remainingSeconds);

            Image iconToDraw = null;
            if (paddle.getWidth() > Constants.DEFAULT_PADDLE_WIDTH) {
                iconToDraw = expandIcon;
            } else if (paddle.getWidth() < Constants.DEFAULT_PADDLE_WIDTH) {
                iconToDraw = shrinkIcon;
            }

            if (iconToDraw != null) {
                gc.drawImage(iconToDraw, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;

        }

        if (paddle.getSpeedRemainingTime() > 0) {
            long remainingNano = (long)paddle.getSpeedRemainingTime();

            double remainingSeconds = remainingNano / 1_000_000_000.0;
            String formattedTime = String.format(": %.2fs", remainingSeconds);

            Image iconToDraw = null;

            if (paddle.getSpeed() < Constants.CURRENT_PADDLE_SPEED) {
                iconToDraw = slowIcon;
            }

            if (iconToDraw != null) {
                gc.drawImage(iconToDraw, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
            }
            gc.fillText(formattedTime, TIMER_START_X + ICON_SIZE + ICON_TEXT_PADDING, currentTimerY);

            currentTimerY += TIMER_LINE_HEIGHT;

        }


        if (gm.isSafetyNetActive()) {
            long remainingNano = (long)gm.getSafetyNetRemainingTime();
            if (remainingNano > 0) {
                double remainingSeconds = remainingNano / 1_000_000_000.0;
                String formattedTime = String.format(": %.2fs", remainingSeconds);

                if (safetyIcon != null) {
                    gc.drawImage(safetyIcon, TIMER_START_X, currentTimerY - ICON_SIZE / 1.5, ICON_SIZE, ICON_SIZE);
                }
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
}