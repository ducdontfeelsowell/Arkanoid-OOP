package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Quản lý việc hiển thị một animation nổ tại một vị trí cụ thể.
 * Tải ảnh một lần và chia sẻ cho tất cả các đối tượng nổ.
 */
public class ExplosionAnimation extends GameObject {

    // Danh sách ảnh tĩnh (static), chỉ tải 1 LẦN
    private static List<Image> explosionFrames;

    // Kích thước của ảnh (giả định tất cả các frame như nhau)
    private static double FRAME_WIDTH = 96;
    private static double FRAME_HEIGHT = 96;

    private int currentFrame;
    private boolean finished;
    private long lastFrameTime;

    private static final long FRAME_DURATION_NANO = 80_000_000L;

    static {
        explosionFrames = new ArrayList<>();
        try {
            for (String path : Constants.PATH_TO_EXPLOSION_ANIM) {
                Image frame = new Image(Objects.requireNonNull(
                        ExplosionAnimation.class.getResourceAsStream(path)));
                explosionFrames.add(frame);
            }

            if (!explosionFrames.isEmpty()) {
                FRAME_WIDTH = explosionFrames.get(0).getWidth();
                FRAME_HEIGHT = explosionFrames.get(0).getHeight();
            }

        } catch (Exception e) {
            System.err.println("LỖI NGHIÊM TRỌNG: Không thể tải ảnh animation nổ!");
            e.printStackTrace();
            explosionFrames.clear();
        }
    }

    /**
     * Tạo một hiệu ứng nổ mới tại (x, y)
     * (x, y) là TÂM của vụ nổ.
     */
    public ExplosionAnimation(double x, double y) {

        super(x - FRAME_WIDTH / 2, y - FRAME_HEIGHT / 2, FRAME_WIDTH, FRAME_HEIGHT);

        this.currentFrame = 0;
        this.finished = false;
        this.lastFrameTime = System.nanoTime();
    }

    @Override
    public void update() {
        if (finished || explosionFrames.isEmpty()) {
            return;
        }

        long now = System.nanoTime();
        if (now - lastFrameTime > FRAME_DURATION_NANO) {
            currentFrame++;

            if (currentFrame >= explosionFrames.size()) {
                finished = true;
            }
            lastFrameTime = now;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (finished || explosionFrames.isEmpty()) {
            return;
        }

        Image frameToDraw = explosionFrames.get(currentFrame);
        gc.drawImage(frameToDraw, x, y, width, height);
    }

    public boolean isFinished() {
        return finished;
    }
}