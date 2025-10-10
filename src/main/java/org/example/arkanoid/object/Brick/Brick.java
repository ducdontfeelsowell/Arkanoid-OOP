package org.example.arkanoid.object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.object.GameObject;

public class Brick extends GameObject {

    private int hitPoints;
    private int type;
    private boolean destroyed;

    public Brick(double x, double y, double width, double height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroyed = false;
    }

    @Override
    public void update() {
        // Brick không di chuyển nên không cần làm gì ở đây
    }

    @Override
    public void render(GraphicsContext gc) {
        Color brickColor;
        Color highlightColor;

        switch (getType()) {
            case 1: // Normal brick
                brickColor = Color.rgb(100, 200, 100);
                highlightColor = Color.rgb(150, 255, 150, 0.7);
                break;
            case 2: // Strong brick
                brickColor = Color.rgb(200, 100, 100);
                highlightColor = Color.rgb(255, 150, 150, 0.7);
                break;
            case 3: // Indestructible brick
                brickColor = Color.rgb(150, 150, 150);
                highlightColor = Color.rgb(200, 200, 200, 0.7);
                break;
            default:
                brickColor = Color.GRAY;
                highlightColor = Color.LIGHTGRAY;
        }

        // Main brick
        gc.setFill(brickColor);
        gc.fillRoundRect(getX() + 1, getY() + 1,
                getWidth() - 2, getHeight() - 2, 5, 5);

        // Highlight
        gc.setFill(highlightColor);
        gc.fillRoundRect(getX() + 1, getY() + 1,
                getWidth() - 2, getHeight() / 3, 5, 5);

        // Border
        gc.setStroke(Color.rgb(50, 50, 50));
        gc.setLineWidth(1);
        gc.strokeRoundRect(getX() + 1, getY() + 1,
                getWidth() - 2, getHeight() - 2, 5, 5);

        // Show hit points for strong bricks
        if (getType() == 2) {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.fillText(String.valueOf(getHitPoints()),
                    getX() + getWidth() / 2 - 5,
                    getY() + getHeight() / 2 + 5);
        }
    }

    public void takeHit() {
        if (!destroyed && hitPoints != Integer.MAX_VALUE) {
            hitPoints--;
            if (hitPoints <= 0) {
                destroyed = true;
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getType() {
        return type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}