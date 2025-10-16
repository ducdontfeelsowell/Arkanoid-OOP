package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image; // <<< THÊM MỚI
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.render.*;


public class GameRenderer {
    private final GraphicsContext gc;
    private final Image backgroundImage; // <<< THÊM MỚI

    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
        // <<< THÊM MỚI: Tải ảnh nền.
        // Thay đổi "/Images/background.png" thành đường dẫn thực tế đến tệp ảnh của bạn.
        try {
            this.backgroundImage = new Image(getClass().getResourceAsStream("/Images/background/menu_Background.jpg"));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh background!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Vẽ toàn bộ game state
     */
    public void renderObject(Paddle paddle, Ball ball, Brick[][] bricks, int score, int lives) {
        // Clear canvas
        // --- THAY ĐỔI: Thay vì tô màu, hãy vẽ ảnh nền ---
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        } else {
            // Dự phòng nếu không tải được ảnh
            gc.setFill(Color.rgb(20, 20, 40));
            gc.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        }
        // --------------------------------------------------

        // Render game objects
        RenderBricks.render(bricks, gc);
        RenderPaddle.render(paddle, gc);
        RenderBall.render(ball, gc);
        RenderUI.render(score, lives, gc);
    }

    public void renderGameOver(int score) {
        RenderGameOver.render(score, gc);
    }

    public void renderWin(int score) {
        RenderWin.render(score, gc);
    }
}