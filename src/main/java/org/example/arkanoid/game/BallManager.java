package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.game.SoundManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BallManager {

    public List<Ball> balls;

    public BallManager() {
        this.balls = new ArrayList<>();
    }

    public void update() {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.move();

            if (ball.isOffScreen()) {
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_BALL_OUT);
                ball.clearTrail();
                iterator.remove();
            }
        }
    }

    public void addBall(Paddle paddle) {
        if (balls.size() == 0) {
            balls.add(new Ball(
                    paddle.getX() + paddle.getWidth() / 2 - Constants.DEFAULT_BALL_SIZE / 2,
                    paddle.getY() - Constants.DEFAULT_BALL_SIZE - 1,
                    Constants.DEFAULT_BALL_OFFSET,
                    Constants.DEFAULT_BALL_DX,
                    Constants.DEFAULT_BALL_DY
            ));
        } else {
            for (int i = 0; i < balls.size(); i++) {
                double minOff = Constants.DEFAULT_BALL_OFFSET;
                double maxOff = Constants.DEFAULT_BALL_OFFSET_CAP;
                double randomOff = minOff + (maxOff - minOff) * Math.random();
                Ball temp = balls.get(i);
                Ball nouveauBallon = new Ball(
                        temp.getX(),
                        temp.getY(),
                        randomOff,
                        temp.getDx(),
                        1
                );
            }
        }
    }

    public boolean isEmpty() {
        return (balls.size() == 0);
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
