package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Item;

public class RenderItem {
    public static void render(Item item, GraphicsContext gc) {
        item.render(gc);
    }
}
