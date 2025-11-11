package org.example.arkanoid.object.Brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.arkanoid.config.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfBrick extends Brick {


    private static Image staticFrame;
    private static List<Image> animationFrames;


    private boolean isAnimating;
    private int currentFrame;
    private long lastFrameTime;

    private static final long FRAME_DURATION_NANO = 50_000_000L;

    static {
        animationFrames = new ArrayList<>();
        try {
            staticFrame = new Image(Objects.requireNonNull(
                    InfBrick.class.getResourceAsStream(Constants.PATH_TO_INF_BRICK_STATIC)
            ));

            for (String path : Constants.PATH_TO_INF_BRICK_ANIM) {
                Image frame = new Image(Objects.requireNonNull(
                        InfBrick.class.getResourceAsStream(path)
                ));
                animationFrames.add(frame);
            }
        } catch (Exception e) {
            System.err.println("LỖI: Không thể tải ảnh cho gạch InfBrick!");
            e.printStackTrace();
            animationFrames.clear();
            staticFrame = null;
        }
    }

    public InfBrick(double x, double y, double width, double height, int hitPoints, int type) {
        super(x, y, width, height, hitPoints, type);
        this.isAnimating = false;
        this.currentFrame = 0;
        this.lastFrameTime = 0;
    }

    /**
     * Ghi đè phương thức render của lớp Brick
     */
    @Override
    public void render(GraphicsContext gc) {
        Image frameToDraw = staticFrame; // Mặc định là ảnh tĩnh

        // Nếu đang trong trạng thái animation
        if (isAnimating) {
            long now = System.nanoTime();

            // Nếu đây là frame đầu tiên, bắt đầu đếm thời gian
            if (lastFrameTime == 0) {
                lastFrameTime = now;
            }

            // Chuyển frame nếu đủ thời gian
            if (now - lastFrameTime > FRAME_DURATION_NANO) {
                currentFrame++;
                lastFrameTime = now;
            }

            // Nếu animation vẫn còn
            if (currentFrame < animationFrames.size()) {
                frameToDraw = animationFrames.get(currentFrame);
            } else {
                // Animation kết thúc, quay lại trạng thái tĩnh
                isAnimating = false;
                currentFrame = 0;
                lastFrameTime = 0;
                frameToDraw = staticFrame;
            }
        }

        // Vẽ frame được chọn
        if (frameToDraw != null) {
            gc.drawImage(frameToDraw, getX(), getY(), getWidth(), getHeight());
        } else {
            // Dự phòng nếu ảnh lỗi: vẽ theo logic của lớp cha
            super.render(gc);
        }
    }

    /**
     * Kích hoạt animation va chạm (được gọi từ HandleBrickCollision)
     */
    public void triggerAnimation() {
        if (!isAnimating) {
            this.isAnimating = true;
            this.currentFrame = 0;
            this.lastFrameTime = 0;
        }
    }
}