package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;

public class RenderWin {
    public static void render(int finalScore, GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        gc.setFill(Color.GOLD);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        gc.fillText("YOU WIN!", Constants.SCREEN_WIDTH / 2 - 200, Constants.SCREEN_HEIGHT / 2 - 50);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 40));
        gc.fillText("Final Score: " + finalScore, Constants.SCREEN_WIDTH / 2 - 150, Constants.SCREEN_HEIGHT / 2 + 30);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        gc.fillText("Press ESC to return to menu", Constants.SCREEN_WIDTH / 2 - 180, Constants.SCREEN_HEIGHT / 2 + 100);
    }
}
