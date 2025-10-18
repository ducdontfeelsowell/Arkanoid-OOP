package org.example.arkanoid.logic;



import org.example.arkanoid.game.GameManager;

import org.example.arkanoid.object.Ball;

import org.example.arkanoid.object.Brick.Brick;



public class CheckBallBrickCollision {

    public static void check(Ball ball, Brick[][] bricks, GameManager gm) {

        int score = gm.getScore();

        for (Brick[] row : bricks) {

            for (Brick brick : row) {

                if (brick != null && !brick.isDestroyed() && ball.isCollidingWith(brick)) {

                    HandleBrickCollision.handle(ball, brick, gm);

                    return; // Chỉ xử lý 1 brick mỗi frame

                }

            }

        }

    }

}