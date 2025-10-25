package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

public class Item extends MoveAbleObject {

    public enum ItemType {
        EXPAND_PADDLE,    // Mở rộng paddle
        SHRINK_PADDLE,    // Thu nhỏ paddle
        EXTRA_LIFE       // Thêm mạng
    }

    private ItemType type;
    private Image itemImage;
    private boolean collected;

    public Item(double x, double y, ItemType type) {
        super(x, y, Constants.DEFAULT_ITEM_WIDTH, Constants.DEFAULT_ITEM_HEIGHT,
                Constants.DEFAULT_ITEM_DX, Constants.DEFAULT_ITEM_DY);
        this.type = type;
        this.collected = false;
        loadImage();
    }

    private void loadImage() {
        String imagePath = "";

        switch (type) {
            case EXPAND_PADDLE:
                imagePath = "/images/mechanic/expand_paddle.png";
                break;
            case SHRINK_PADDLE:
                imagePath = "/Images/mechanic/shrink_paddle.png";
                break;
            case EXTRA_LIFE:
                imagePath = "/Images/mechanic/extra_heart.png";
                break;
        }

        try {
            itemImage = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho item: " + imagePath);
            itemImage = null;
        }
    }

    @Override
    public void move() {
        y += dy; // Rơi xuống dưới
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (collected) return;

        if (itemImage != null) {
            gc.drawImage(itemImage, x, y, width, height);
        } else {
            // Vẽ hình chữ nhật màu dự phòng nếu không có ảnh
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

    // Getters and Setters
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