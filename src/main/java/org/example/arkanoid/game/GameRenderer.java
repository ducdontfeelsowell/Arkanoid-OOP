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
    private final Image backgroundImage2; // <<< THÊM MỚI

    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
        try {
            this.backgroundImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_BACKGROUND));
            this.backgroundImage2 = new Image(getClass().getResourceAsStream(Constants.PATH_TO_MENU_BACKGROUND));

        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh background!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Vẽ toàn bộ game state
     */
    public void renderObject(Paddle paddle, Ball ball, Brick[][] bricks, int score, int lives) {
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            gc.drawImage(backgroundImage2, 350, 0, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT);
        } else {
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