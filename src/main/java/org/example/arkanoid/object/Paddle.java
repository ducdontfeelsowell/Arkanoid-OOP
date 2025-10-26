package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.arkanoid.config.Constants;

import java.util.Objects;

public class Paddle extends MoveAbleObject {

    private double speed;

    private Image image0;
    private Image image1;
    private Image image2;

    private Image shooterImage0;

    private int currentImageIndex = 0;
    private long lastToggleTime = 0;
    private final long TOGGLE_INTERVAL = 200_000_000; // 200ms

    private boolean isShooter = false;
    private double shooterEndTime = 0;

    public Paddle() {
        super(
                Constants.DEFAULT_PADDLE_POSITION_X,
                Constants.DEFAULT_PADDLE_POSITION_Y,
                Constants.DEFAULT_PADDLE_WIDTH,
                Constants.DEFAULT_PADDLE_HEIGHT,
                Constants.DEFAULT_PADDLE_DX,
                Constants.DEFAULT_PADDLE_DY);

        this.speed = Constants.DEFAULT_PADDLE_SPEED;

        try {
            image0 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_0)));
            image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_1)));
            image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_PADDLE_2)));

            shooterImage0 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.PATH_TO_SHOOTER_PADDLE)));

        } catch (Exception e) {
            System.err.println("Không thể tải ảnh cho paddle!");
            e.printStackTrace();
            image0 = null;
            image1 = null;
            image2 = null;
            shooterImage0 = null;
        }
    }

    @Override
    public void move() {
        x += dx;

        if (x < Constants.PLAY_AREA_LEFT) {
            x = Constants.PLAY_AREA_LEFT;
        } else if (x + width > Constants.SCREEN_WIDTH - Constants.PLAY_AREA_RIGHT_MARGIN) {
            x = Constants.SCREEN_WIDTH - width - Constants.PLAY_AREA_RIGHT_MARGIN;
        }
    }

    public void moveLeft() {
        dx = -speed;
    }

    public void moveRight() {
        dx = +speed;
    }

    public void stopMove() {
        dx = 0;
    }

    @Override
    public void update() {
        move();

        if (isShooter && System.nanoTime() > shooterEndTime) {
            isShooter = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        long currentTime = System.nanoTime();
        if (currentTime - lastToggleTime > TOGGLE_INTERVAL) {
            currentImageIndex = (currentImageIndex + 1) % 3;
            lastToggleTime = currentTime;
        }

        Image currentImage;
        if (isShooter) {
            switch (currentImageIndex) {
                case 0: currentImage = shooterImage0; break;
                default: currentImage = shooterImage0; break;
            }
        } else {
            switch (currentImageIndex) {
                case 0: currentImage = image0; break;
                case 1: currentImage = image1; break;
                case 2: currentImage = image2; break;
                default: currentImage = image0; break;
            }
        }

        if (currentImage != null) {
            gc.drawImage(currentImage, getX(), getY(), getWidth(), getHeight());
        } else {
            gc.setFill(Color.YELLOW);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void activateShooter() {
        this.isShooter = true;
        this.shooterEndTime = System.nanoTime() + Constants.DEFAULT_SHOOTER_DURATION;
    }

    public void resetState() {
        this.isShooter = false;
        this.width = Constants.DEFAULT_PADDLE_WIDTH;
        this.x = Constants.DEFAULT_PADDLE_POSITION_X;
    }

    public boolean isShooter() {
        return isShooter;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}