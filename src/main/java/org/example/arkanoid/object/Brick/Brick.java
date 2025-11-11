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
    private Image brokenBrickImage; // <-- THÊM BIẾN NÀY
    private Color brickColor;

    public Brick(double x, double y, double width, double height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroyed = false;
        // (Phần switch(this.type) cho brickColor có thể giữ nguyên hoặc xóa nếu không dùng)
        switch (this.type) {
            case 1: this.brickColor = Color.rgb(150, 150, 150); break; // màu xám
            case 2: this.brickColor = Color.rgb(255, 150, 80); break;  // Màu cam
            case 3: this.brickColor = Color.rgb(120, 120, 120); break; // Màu xám (bất tử)
            case 4: this.brickColor = Color.rgb(255, 80, 80); break;   // Màu đỏ (nổ)
            default: this.brickColor = Color.GRAY;
        }
        loadImage(); // Tải ảnh
        setScoreBasedOnType(); // Đặt điểm
    }

    private void setScoreBasedOnType() {
        // (Bạn có thể cập nhật điểm ở đây nếu muốn, tôi sẽ giữ nguyên logic cũ)
        switch (this.type) {
            case 1:
                this.score = 50;
                break;
            case 2:
                this.score = 100;
                break;
            case 3:
                this.score = 1000000000;
                break;
            case 4:
                this.score = 50;
                break;
            default:
                this.score = 0; // Gạch 5-14 hiện chưa có điểm, bạn có thể thêm vào đây
                break;
        }
    }

    // *** THAY ĐỔI LỚN Ở HÀM NÀY ***
    private void loadImage() {
        String imagePath = "";
        String brokenImagePath = null; // Mặc định là null

        switch (getType()) {
            // Gạch 1 HP (Types 1, 2, 5, 6)
            case 1: imagePath = Constants.PATH_TO_NORMAL_BRICK1; break;
            case 2: imagePath = Constants.PATH_TO_NORMAL_BRICK2; break;
            case 5: imagePath = Constants.PATH_TO_NORMAL_BRICK5; break;
            case 6: imagePath = Constants.PATH_TO_NORMAL_BRICK6; break;

            // Gạch nổ (Type 4) - giữ nguyên từ code cũ của bạn
            case 4: imagePath = Constants.PATH_TO_NORMAL_BRICK5; break;

            // Gạch 3 HP (Types 7-14) - có ảnh vỡ
            case 7:
                imagePath = Constants.PATH_TO_NORMAL_BRICK7;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK7;
                break;
            case 8:
                imagePath = Constants.PATH_TO_NORMAL_BRICK8;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK8;
                break;
            case 9:
                imagePath = Constants.PATH_TO_NORMAL_BRICK9;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK9;
                break;
            case 10:
                imagePath = Constants.PATH_TO_NORMAL_BRICK10;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK10;
                break;
            case 11:
                imagePath = Constants.PATH_TO_NORMAL_BRICK11;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK11;
                break;
            case 12:
                // Lưu ý: Constants thiếu PATH_TO_NORMAL_BRICK12
                // Tạm dùng KKK theo file Constants
                imagePath = Constants.PATH_TO_NORMAL_BRICKKK;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK12;
                System.err.println("Warning: Gạch type 12 không có ảnh 'normal', dùng 'normalbrick3.png'");
                break;
            case 13:
                imagePath = Constants.PATH_TO_NORMAL_BRICK13;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK13;
                break;
            case 14:
                imagePath = Constants.PATH_TO_NORMAL_BRICK14;
                brokenImagePath = Constants.PATH_TO_BROKEN_BRICK14;
                break;

            case 3: imagePath = Constants.PATH_TO_NORMAL_BRICK7; break;
        }

        try {
            // Tải ảnh chính
            if (imagePath != null && !imagePath.isEmpty()) {
                brickImage = new Image(getClass().getResourceAsStream(imagePath));
            }

            // Tải ảnh vỡ (nếu có)
            if (brokenImagePath != null && !brokenImagePath.isEmpty()) {
                brokenBrickImage = new Image(getClass().getResourceAsStream(brokenImagePath));
            }
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

        /* // vẽ HP
        if (getType() >= 7 && getType() <= 14 && getHitPoints() > 0) {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.fillText(String.valueOf(getHitPoints()),
                    getX() + getWidth() / 2 - 5,
                    getY() + getHeight() / 2 + 5);
        } else if (getType() == 2 && getHitPoints() > 0) {
             gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.fillText(String.valueOf(getHitPoints()),
                    getX() + getWidth() / 2 - 5,
                    getY() + getHeight() / 2 + 5);
        }
        */
    }

    public void takeHit() {
        if (!destroyed && hitPoints != Integer.MAX_VALUE) {
            hitPoints--;
            if (hitPoints <= 0) {
                destroyed = true;
            } else if (hitPoints == 1 && brokenBrickImage != null) {
                this.brickImage = this.brokenBrickImage;
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
            return Color.GRAY;
        }
        return this.brickColor;
    }
}