package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Ball;

public class RenderBall {
    public static void render(Ball ball, GraphicsContext gc) {
        ball.render(gc);
    }
}
