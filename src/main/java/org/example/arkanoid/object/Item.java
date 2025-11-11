package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item extends MoveAbleObject {

    public enum ItemType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        EXTRA_LIFE,
        SHOOTER_PADDLE,
        MULTI_BALL,
        SAFETY_NET,
        SLOW_SPEED,
        COIN_50,
        COIN_100,
        COIN_250,
        COIN_500
    }

    private ItemType type;
    private boolean collected;

    private List<Image> animationFrames;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 100_000_000L;

    public Item(double x, double y, ItemType type) {
        super(x, y, Constants.DEFAULT_ITEM_WIDTH, Constants.DEFAULT_ITEM_HEIGHT,
                Constants.DEFAULT_ITEM_DX, Constants.DEFAULT_ITEM_DY);
        this.type = type;
        this.collected = false;

        this.animationFrames = new ArrayList<>();

        loadImage();
    }

    private void loadImage() {
        try {
            switch (type) {
                case EXPAND_PADDLE:
                    for (String path : Constants.PATH_TO_EXPAND_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;

                case SHRINK_PADDLE:
                    for (String path : Constants.PATH_TO_SHRINK_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;

                case EXTRA_LIFE:
                    animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_EXTRA_LIFE))));
                    break;

                case SHOOTER_PADDLE:
                    for (String path : Constants.PATH_TO_SHOOTER_ITEM_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;
                case MULTI_BALL:
                    for (String path : Constants.PATH_TO_MULTI_BALL_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;
                case SAFETY_NET:
                    for (String path : Constants.PATH_TO_SAFETY_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;
                case SLOW_SPEED:
                    for (String path : Constants.PATH_TO_SLOW_ANIM) {
                        animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
                    }
                    break;
                case COIN_50:
                    animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_COIN_50))));
                    this.width = 60;
                    break;
                case COIN_100:
                    animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_COIN_100))));
                    this.width = 60;
                    break;
                case COIN_250:
                    animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_COIN_250))));
                    this.width = 60;
                    break;
                case COIN_500:
                    animationFrames.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_COIN_500))));
                    this.width = 60;
                    break;
            }
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho item: " + type);
            animationFrames.clear();
        }
    }


    @Override
    public void move() {
        y += dy;
    }

    @Override
    public void update() {
        move();
        long now = System.nanoTime();
        if (now - lastFrameTime > FRAME_DURATION) {
            if (animationFrames != null && !animationFrames.isEmpty()) {
                currentFrame = (currentFrame + 1) % animationFrames.size();
            }
            lastFrameTime = now;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (collected) return;

        if (animationFrames != null && !animationFrames.isEmpty()) {
            Image imageToRender = animationFrames.get(currentFrame);
            gc.drawImage(imageToRender, x, y, width, height);
        } else {
            Color color = getColorForType();
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.WHITE);
            gc.strokeRect(x, y, width, height);
        }
    }

    private Color getColorForType() {
        switch (type) {
            case EXPAND_PADDLE: return Color.GREEN;
            case SHRINK_PADDLE: return Color.RED;
            case EXTRA_LIFE: return Color.GOLD;
            case MULTI_BALL: return Color.CYAN;
            case SAFETY_NET: return Color.BLUEVIOLET;
            case SLOW_SPEED: return Color.LIGHTBLUE;
            case COIN_50:
            case COIN_100:
            case COIN_250:
            case COIN_500:
                return Color.YELLOW;
            default: return Color.GRAY;
        }
    }

    public boolean isOutOfBounds() {
        return y > Constants.SCREEN_HEIGHT;
    }

    public boolean isCollidingWith(GameObject other) {
        return x < other.getX() + other.getWidth() &&
                x + width > other.getX() &&
                y < other.getY() + other.getHeight() &&
                y + height > other.getY();
    }

    public ItemType getType() {
        return type;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}