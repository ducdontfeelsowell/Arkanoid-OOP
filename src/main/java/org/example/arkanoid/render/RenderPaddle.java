package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Paddle;

public class RenderPaddle {
    public static void render(Paddle paddle, GraphicsContext gc) {
        paddle.render(gc);
    }
}
