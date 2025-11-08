package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.game.GameManager; // <-- THÊM IMPORT NÀY

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BallManager {

    public List<Ball> balls;
    private Random random = new Random();

    public BallManager() {
        this.balls = new ArrayList<>();
    }

    // SỬA ĐỔI: Thêm tham số GameManager gm
    public void update(GameManager gm) {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.move();

            // --- SỬA ĐỔI LOGIC: Kiểm tra Safety Net ---
            if (ball.isOffScreen()) {

                // Kiểm tra xem Safety Net có đang bật không
                if (gm.isSafetyNetActive()) {
                    // CÓ, ĐANG BẬT: Nảy bóng trở lại
                    ball.setY(Constants.SCREEN_HEIGHT - ball.getHeight() - 1); // Đặt lại vị trí ngay mép
                    ball.reverseY(); // Đảo chiều Y
                    // Phát tiếng nảy (giống tiếng va tường)
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_WALL_HIT);
                } else {
                    // KHÔNG BẬT: Mất bóng như bình thường
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_BALL_OUT);
                    ball.clearTrail();
                    iterator.remove();
                }
            }
            // --- KẾT THÚC SỬA ĐỔI ---
        }
    }

    public void addBall(Paddle paddle) {
        if (balls.size() == 0) {
            // Logic cũ: Thêm bóng ban đầu (khi bắt đầu hoặc mất mạng)
            balls.add(new Ball(
                    paddle.getX() + paddle.getWidth() / 2 - Constants.DEFAULT_BALL_SIZE / 2,
                    paddle.getY() - Constants.DEFAULT_BALL_SIZE - 1,
                    Constants.DEFAULT_BALL_OFFSET,
                    Constants.DEFAULT_BALL_DX,
                    Constants.DEFAULT_BALL_DY
            ));
        } else {
            // --- Logic MultiBall (x3) ---
            List<Ball> newBallsToAdd = new ArrayList<>();
            double minOff = Constants.DEFAULT_BALL_OFFSET;
            double maxOff = Constants.DEFAULT_BALL_OFFSET_CAP;

            // Lặp qua tất cả các bóng hiện có (bóng gốc)
            for (Ball temp : balls) {

                // Tạo bóng MỚI thứ 1
                double randomOff1 = minOff + (maxOff - minOff) * random.nextDouble();
                Ball newBall1 = new Ball(
                        temp.getX(),
                        temp.getY(),
                        randomOff1,
                        0,
                        -1
                );
                newBallsToAdd.add(newBall1);

                // Tạo bóng MỚI thứ 2
                double randomOff2 = -(minOff + (maxOff - minOff) * random.nextDouble());
                Ball newBall2 = new Ball(
                        temp.getX(),
                        temp.getY(),
                        randomOff2,
                        0,
                        -1
                );
                newBallsToAdd.add(newBall2);
            }
            balls.addAll(newBallsToAdd);
        }
    }

    // THÊM MỚI: Phương thức lấy số lượng bóng
    public int getBallCount() {
        return balls.size();
    }

    // THÊM MỚI: Phương thức lấy hình ảnh bóng đầu tiên để làm icon
    public Image getBallIcon() {
        if (!balls.isEmpty()) {
            return balls.get(0).getBallImage();
        }
        return null;
    }

    public boolean isEmpty() {
        return (balls.size() == 0);
    }

    public void clearAllTrails() {
        for (Ball ball : balls) {
            ball.clearTrail();
        }
    }

    public void render(GraphicsContext gc) {
        for (Ball ball : balls) {
            ball.render(gc);
        }
    }

    public void render(GraphicsContext gc, boolean isInvicible) {
        for (Ball ball : balls) {
            ball.render(gc, isInvicible);
        }
    }
}