package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.render.*;

import java.util.Objects;


public class GameRenderer {
    private final GraphicsContext gc;
    private final Image backgroundImage2; // <<< THÊM MỚI

    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
        try {
            this.backgroundImage2 = new Image(getClass().getResourceAsStream(Constants.PATH_TO_GAME_BACKGROUND));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh background!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Vẽ toàn bộ game state
     */
    public void renderObject(Paddle paddle, BallManager ball, Brick[][] bricks, ItemManager im, BulletManager bm, int score, int lives) {
        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        if (backgroundImage2 != null) {
            gc.drawImage(backgroundImage2, 315, 0, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT);
        } else {
            gc.setFill(Color.rgb(20, 20, 40));
            gc.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        }

        // Render game objects
        RenderBricks.render(bricks, gc);
        RenderPaddle.render(paddle, gc);

        // --- SỬA ĐỔI: Truyền trạng thái của paddle cho bóng ---
        RenderBall.render(ball, gc, paddle.isInvincible());
        // --- KẾT THÚC SỬA ĐỔI ---

        RenderUI.render(score, lives, gc);
        im.render(gc);
        bm.render(gc);
    }

    public void renderGameOver(int score) {
        RenderGameOver.render(score, gc);
    }

    public void renderWin(int score) {
        RenderWin.render(score, gc);
    }
}
