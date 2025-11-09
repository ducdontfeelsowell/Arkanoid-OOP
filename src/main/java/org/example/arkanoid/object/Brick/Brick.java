package org.example.arkanoid.object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.GameObject;
import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private int hitPoints;
    private int type;
    private int score;
    private boolean destroyed;
    private Image brickImage;
    private Color brickColor;

    public Brick(double x, double y, double width, double height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroyed = false;
        switch (this.type) {
            case 1: this.brickColor = Color.rgb(150, 150, 150); break; // màu xám
            case 2: this.brickColor = Color.rgb(255, 150, 80); break;  // Màu cam
            case 3: this.brickColor = Color.rgb(120, 120, 120); break; // Màu xám (bất tử)
            case 4: this.brickColor = Color.rgb(255, 80, 80); break;   // Màu đỏ (nổ)
            default: this.brickColor = Color.GRAY;
        }
        loadImage();
        setScoreBasedOnType();
    }

    private void setScoreBasedOnType() {
        switch (this.type) {
            case 1:
                this.score = 50;
                break;
            case 2:
                this.score = 100;
                break;
            case 3:
                this.score = 50;
                break;
            case 4:
                this.score = 50;
                break;
            default:
                this.score = 0;
                break;
        }
    }

    private void loadImage() {
        String imagePath = "";
        switch (getType()) {
            case 1: imagePath = Constants.PATH_TO_NORMAL_BRICK11; break;
            case 2: imagePath = Constants.PATH_TO_NORMAL_BRICK8; break;
            case 3: imagePath = Constants.PATH_TO_NORMAL_BRICK7; break;
            case 4: imagePath = Constants.PATH_TO_NORMAL_BRICK5; break;
        }
        try {
            brickImage = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho gạch loại " + getType() + ": " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (destroyed) return;

        if (brickImage != null) {
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());
        }

        if (getType() == 2 && getHitPoints() > 0) {
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

    public int getScore() {
        return this.score;
    }

    public int getType() {
        return type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Color getBrickColor() {
        if (this.brickColor == null) {
            return Color.GRAY; // Trả về màu xám nếu có lỗi
        }
        return this.brickColor;
    }
}
