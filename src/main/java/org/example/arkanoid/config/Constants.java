package org.example.arkanoid.config;

public class Constants {
    // TODO: sửa lại các hằng số để vừa mắt

    public final static double SCREEN_WIDTH = 1560;
    public final static double SCREEN_HEIGHT = 690;

    public final static double DEFAULT_PADDLE_WIDTH = 120;
    public final static double DEFAULT_PADDLE_HEIGHT = 20;
    public final static double DEFAULT_PADDLE_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_PADDLE_WIDTH / 2;
    public final static double DEFAULT_PADDLE_POSITION_Y = SCREEN_HEIGHT - 100;
    public final static double DEFAULT_PADDLE_DX = 0;
    public final static double DEFAULT_PADDLE_DY = 0;
    public final static double DEFAULT_PADDLE_SPEED = 8;

    public final static double DEFAULT_BALL_SIZE = 15;
    public final static double DEFAULT_BALL_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_BALL_SIZE / 2;
    public final static double DEFAULT_BALL_POSITION_Y = DEFAULT_PADDLE_POSITION_Y - DEFAULT_BALL_SIZE - 5;
    public final static double DEFAULT_BALL_DX = 5;
    public final static double DEFAULT_BALL_DY = -5;
    public final static double DEFAULT_BALL_SPEED = 4;
    public final static int DEFAULT_BALL_DIRECTION_X = 1;
    public final static int DEFAULT_BALL_DIRECTION_Y = -1;

    public final static int HIT_POINTS_NORMAL_BRICK = 1;
    public final static int HIT_POINTS_STRONG_BRICK = 3;
    public final static int HIT_POINTS_INF_BRICK = Integer.MAX_VALUE;

    public final static double BRICK_WIDTH = 80;
    public final static double BRICK_HEIGHT = 30;

    public final static int INITIAL_LIVES = 3;
    public final static int POINTS_PER_BRICK = 10;

    // TODO cải tiến để đọc nhiều map
    public final static String MAP_PATH = "src/main/resources/Maps/map.txt";
}