package org.example.arkanoid.object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color; // Cần thiết cho Text (điểm số)
import javafx.scene.text.Font; // Cần thiết cho Text (điểm số)
import javafx.scene.text.FontWeight; // Cần thiết cho Text (điểm số)
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.GameObject;

public class Brick extends GameObject {

    private int hitPoints;
    private int type;
    private boolean destroyed;
    private Image brickImage;

    public Brick(double x, double y, double width, double height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroyed = false;

        loadImage();
    }

    /**
     * Tải hình ảnh dựa trên loại gạch (type).
     * SỬA ĐỔI: Đường dẫn đã được điều chỉnh để khớp với cấu trúc thư mục.
     */
    private void loadImage() {
        String imagePath = "";

        switch (getType()) {
            case 1:
                imagePath = Constants.PATH_TO_BRICK_1;
                break;
            case 2:
                imagePath = Constants.PATH_TO_BRICK_2;
                break;
            case 3:
                imagePath = Constants.PATH_TO_BRICK_3;
                break;
            case 4:
                imagePath = Constants.PATH_TO_BRICK_4;
                break;
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
        // Brick không di chuyển
    }

    @Override
    public void render(GraphicsContext gc) {
        if (brickImage != null) {
            gc.drawImage(Constants.brick_state_list1[9], getX(), getY(), getWidth(), getHeight());
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