package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Brick.Brick;

public class RenderBricks {
    public static void render(Brick[][] bricks, GraphicsContext gc) {
        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (brick != null && !brick.isDestroyed()) {
                    RenderBrick.render(brick, gc);
                }
            }
        }
    }
}
