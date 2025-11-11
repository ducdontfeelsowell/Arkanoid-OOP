package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.BulletManager;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.util.List;

public class CheckCollisions {
    public static void check(BallManager ballManager, Paddle paddle, Brick[][] bricks, GameManager gm, ItemManager im, BulletManager bm) {

        for (int i = 0; i < ballManager.balls.size(); i++) {
            Ball ball = ballManager.balls.get(i);

            double dx = ball.getDx();
            double dy = ball.getDy();

            double maxSpeed = Math.max(Math.abs(dx), Math.abs(dy));
            double safeBrickHeight = (Constants.BRICK_HEIGHT <= 0) ? 15.0 : Constants.BRICK_HEIGHT;

            int steps = (int) (maxSpeed / (safeBrickHeight / 2.0)) + 1;

            if (steps <= 0) steps = 1;

            double stepDx = dx / steps;
            double stepDy = dy / steps;

            boolean brickHit = false;

            for (int j = 0; j < steps; j++) {

                ball.setX(ball.getX() + stepDx);
                ball.setY(ball.getY() + stepDy);

                CheckBallPaddleCollision.check(ball, paddle);

                brickHit = CheckBallBrickCollision.check(ball, bricks, gm, im);

                if (ball.getDx() != dx || ball.getDy() != dy) {
                    dx = ball.getDx();
                    dy = ball.getDy();
                    stepDx = dx / steps;
                    stepDy = dy / steps;

                    if (brickHit) {
                        break;
                    }
                }
            }
        }

        CheckBallOutOfBounds.check(ballManager, paddle, gm, im, bm);
    }
}