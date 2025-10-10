package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;

public class RenderUI {
    public static void render(int score, int lives, GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Score
        gc.fillText("SCORE: " + score, 20, 40);

        // Lives
        gc.fillText("LIVES: " + lives, Constants.SCREEN_WIDTH - 150, 40);
    }
}
