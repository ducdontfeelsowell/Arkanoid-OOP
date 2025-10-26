package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;

public class RenderUI {

    private static long lastFrameTime = System.nanoTime();
    private static int frameCount = 0;
    private static int currentFPS = 0;
    private static long fpsUpdateTimer = 0;

    public static void render(int score, int lives, GraphicsContext gc) {
        // Tính FPS
        calculateFPS();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Score (trái)
        gc.fillText("SCORE: " + score, 20, 40);

        // Lives (phải)
        gc.fillText("LIVES: " + lives, Constants.SCREEN_WIDTH - 150, 40);

        // FPS (kế bên Lives)
        gc.fillText("FPS: " + currentFPS, Constants.SCREEN_WIDTH - 150, 70);
    }

    /**
     * Tính toán FPS mỗi giây
     */
    private static void calculateFPS() {
        long currentTime = System.nanoTime();
        frameCount++;
        fpsUpdateTimer += currentTime - lastFrameTime;
        lastFrameTime = currentTime;

        // Cập nhật FPS mỗi giây (1,000,000,000 nanoseconds)
        if (fpsUpdateTimer >= 1_000_000_000L) {
            currentFPS = frameCount;
            frameCount = 0;
            fpsUpdateTimer = 0;
        }
    }
}