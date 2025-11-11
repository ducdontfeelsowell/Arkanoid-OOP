package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.game.BallManager;

public class RenderBall {
    public static void render(BallManager ballManager, GraphicsContext gc) {
        ballManager.render(gc);
    }

    public static void render(BallManager ballManager, GraphicsContext gc, boolean isInvincible) {
        ballManager.render(gc, isInvincible);
    }
}
