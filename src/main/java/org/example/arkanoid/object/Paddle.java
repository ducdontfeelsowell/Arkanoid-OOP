package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

public class Paddle extends MoveAbleObject {

    private double speed;

    private Image image1; // Hình ảnh thứ nhất
    private Image image2; // Hình ảnh thứ hai
    private boolean useImage1 = true; // Biến cờ để chuyển đổi hình ảnh
    private long lastToggleTime = 0; // Thời gian cuối cùng chuyển đổi hình ảnh
    private final long TOGGLE_INTERVAL = 200_000_000; // Khoảng thời gian nhấp nháy (200ms) tính bằng nanoseconds


    public Paddle(double x, double y, double width, double height,
                  double dx, double dy, double speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        try {
            image1 = new Image(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_1)); // Đường dẫn đến ảnh 1
            image2 = new Image(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_2)); // Đường dẫn đến ảnh 2
        } catch (Exception e) {
            System.err.println("Lỗi tải ảnh cho Paddle!");
            e.printStackTrace();
            // Xử lý nếu ảnh không tải được (ví dụ: dùng màu mặc định)
            image1 = null;
            image2 = null;
        }

    }

    @Override
    public void move() {
        // Paddle chỉ di chuyển theo trục X
        x += dx;

        // Giới hạn trong khung màn hình
        if (x < 0) {
            x = 0;
        } else if (x + width > Constants.SCREEN_WIDTH) {
            x = Constants.SCREEN_WIDTH - width;
        }
    }

    public void moveLeft() {
        dx = -speed;
        move();
    }

    public void moveRight() {
        dx = +speed;
        move();
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        Image currentImage = useImage1 ? image1 : image2;

        long currentTime = System.nanoTime();
        if (currentTime - lastToggleTime > TOGGLE_INTERVAL) {
            useImage1 = !useImage1; // Chuyển đổi cờ
            lastToggleTime = currentTime;
        }

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            // Nếu không tải được ảnh, vẽ hình chữ nhật màu vàng làm dự phòng
            gc.setFill(javafx.scene.paint.Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
