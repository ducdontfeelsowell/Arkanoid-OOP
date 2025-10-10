package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Brick.Brick;

public class RenderBrick {
    public static void render(Brick brick, GraphicsContext gc) {
        brick.render(gc);
    }
}
