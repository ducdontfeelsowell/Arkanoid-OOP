package org.example.arkanoid.object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color; // Cần thiết cho Text (điểm số)
import javafx.scene.text.Font; // Cần thiết cho Text (điểm số)
import javafx.scene.text.FontWeight; // Cần thiết cho Text (điểm số)
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
        String imagePath;

        switch (getType()) {
            case 1:
                imagePath = "/Images/brick/1.png"; // SỬA ĐỔI ĐƯỜNG DẪN
                break;
            case 2:
                imagePath = "/Images/brick/2.png"; // SỬA ĐỔI ĐƯỜNG DẪN
                break;
            case 3:
                imagePath = "/Images/brick/3.png"; // SỬA ĐỔI ĐƯỜNG DẪN
                break;
            case 4:
                imagePath = "/Images/brick/4.png"; // SỬA ĐỔI ĐƯỜNG DẪN
                break;
            default:
                imagePath = "/Images/brick/4.png"; // SỬA ĐỔI ĐƯỜNG DẪN
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
            // VẼ ẢNH GẠCH
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());
        }

        // VẼ ĐIỂM SỐ
        if (getType() == 2 && getHitPoints() > 0) {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.fillText(String.valueOf(getHitPoints()),
                    getX() + getWidth() / 2 - 5,
                    getY() + getHeight() / 2 + 5);
        }
    }

    // Các phương thức khác giữ nguyên
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