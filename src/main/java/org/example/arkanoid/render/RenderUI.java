package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;

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

    // --- SỬA ĐỔI: Điều chỉnh layout cho LIVES và TIM ---

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
    // --- KẾT THÚC SỬA ĐỔI ---

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
    }

    public static void render(int score, int lives, GraphicsContext gc) {
        // Tính FPS (giữ nguyên)
        calculateFPS();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Score (trái) - (giữ nguyên)
        gc.fillText("SCORE: " + score, 20, 40);

        // --- SỬA ĐỔI: Vẽ "LIVES:" ---
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

        // --- SỬA ĐỔI: Vị trí FPS ---

        // Tính Y cho FPS (nằm dưới 2 hàng tim)
        double fpsY = HEART_ROW_2_Y + HEART_SIZE + 20;

        // Vẽ FPS, căn X thẳng hàng với chữ "LIVES:"
        gc.fillText("FPS: " + currentFPS, UI_RIGHT_COLUMN_X, fpsY);

        // --- KẾT THÚC SỬA ĐỔI ---
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