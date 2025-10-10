package org.example.arkanoid.logic;

import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

public class CheckCollisions {
    public static void check(Ball ball, Paddle paddle, Brick[][] bricks, GameManager gm) {
        // Implementation of collision detection and response
        CheckBallPaddleCollision.check(ball, paddle);
        CheckBallBrickCollision.check(ball, bricks, gm);
        CheckBallOutOfBounds.check(ball, paddle, gm);
    }
}
