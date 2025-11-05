package org.example.arkanoid.logic;

import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.util.List;

public class CheckCollisions {
    public static void check(BallManager ballManager, Paddle paddle, Brick[][] bricks, GameManager gm, ItemManager im) {
        for (int i = 0; i < ballManager.balls.size(); i++) {
            CheckBallPaddleCollision.check(ballManager.balls.get(i), paddle);
            CheckBallBrickCollision.check(ballManager.balls.get(i), bricks, gm, im);
        }
        CheckBallOutOfBounds.check(ballManager, paddle, gm);
    }
}
