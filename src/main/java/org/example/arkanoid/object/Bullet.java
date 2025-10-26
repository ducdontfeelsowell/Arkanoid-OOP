package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bullet extends MoveAbleObject {
    private List<Image> animationFrames;
    private int currentFrame = 0;

    public Bullet(double x, double y) {
        super(x - Constants.DEFAULT_BULLET_WIDTH / 2,
                y,
                Constants.DEFAULT_BULLET_WIDTH,
                Constants.DEFAULT_BULLET_HEIGHT,
                Constants.DEFAULT_BULLET_DX,
                Constants.DEFAULT_BULLET_DY);

        this.animationFrames = new ArrayList<>();
        loadImage();
    }

    private void loadImage() {
        try {
            animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_SHOOTS))));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho đạn: " + e.getMessage());
            e.printStackTrace();
            animationFrames.clear();
        }
    }

    @Override
    public void move() {
        y += dy; // Di chuyển (dy âm = đi lên)
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (animationFrames != null && !animationFrames.isEmpty()) {
            Image imageToRender = animationFrames.get(currentFrame);
            gc.drawImage(imageToRender, x, y, width, height);
        } else {
            gc.setFill(Color.YELLOW);
            gc.fillRect(x, y, width, height);
        }
    }

    public boolean isOffScreen() {
        return y + height < 0; // Ra khỏi màn hình phía trên
    }

    public boolean isCollidingWith(GameObject other) {
        return x < other.getX() + other.getWidth() &&
                x + width > other.getX() &&
                y < other.getY() + other.getHeight() &&
                y + height > other.getY();
    }
}