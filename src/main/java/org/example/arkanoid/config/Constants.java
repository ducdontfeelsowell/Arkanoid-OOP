package org.example.arkanoid.config;

public class Constants {
    // TODO: sửa lại các hằng số để vừa mắt

    public final static double SCREEN_WIDTH = 720;
    public final static double SCREEN_HEIGHT = 700;
    public final static double DEFAULT_SCREEN_X = -20;
    public final static double DEFAULT_SCREEN_Y = 400;

    public final static double DEFAULT_PADDLE_WIDTH = 120;
    public final static double DEFAULT_PADDLE_HEIGHT = 20;
    public final static double DEFAULT_PADDLE_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_PADDLE_WIDTH / 2;
    public final static double DEFAULT_PADDLE_POSITION_Y = SCREEN_HEIGHT - 100;
    public final static double DEFAULT_PADDLE_DX = 0;
    public final static double DEFAULT_PADDLE_DY = 0;
    public final static double DEFAULT_PADDLE_SPEED = 3;

    public final static double DEFAULT_BALL_SIZE = 15;
    public final static double DEFAULT_BALL_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_BALL_SIZE / 2;
    public final static double DEFAULT_BALL_POSITION_Y = DEFAULT_PADDLE_POSITION_Y - DEFAULT_BALL_SIZE - 5;
    public final static double DEFAULT_BALL_DX = 5;
    public final static double DEFAULT_BALL_DY = -5;
    public final static double DEFAULT_BALL_SPEED = 4;
    public final static int    DEFAULT_BALL_DIRECTION_X = 1;
    public final static int    DEFAULT_BALL_DIRECTION_Y = -1;

    public final static int HIT_POINTS_NORMAL_BRICK = 1;
    public final static int HIT_POINTS_STRONG_BRICK = 3;
    public final static int HIT_POINTS_INF_BRICK = Integer.MAX_VALUE;

    public final static double BRICK_WIDTH = 30;
    public final static double BRICK_HEIGHT = 30;

    public final static int INITIAL_LIVES = 3;
    public final static int POINTS_PER_BRICK = 10;

    // TODO cải tiến để đọc nhiều map
    public final static String MAP1_PATH = "src/main/resources/Maps/map1.txt";
    public final static String MAP2_PATH = "src/main/resources/Maps/map2.txt";
    public final static String MAP3_PATH = "src/main/resources/Maps/map3.txt";
    public final static String MAP4_PATH = "src/main/resources/Maps/map4.txt";
    public final static String MAP5_PATH = "src/main/resources/Maps/map5.txt";
    public final static String MAP6_PATH = "src/main/resources/Maps/map6.txt";
    public final static String MAP7_PATH = "src/main/resources/Maps/map7.txt";
    public final static String MAP8_PATH = "src/main/resources/Maps/map8.txt";
    public final static String MAP9_PATH = "src/main/resources/Maps/map9.txt";
    public final static String MAP10_PATH = "src/main/resources/Maps/map10.txt";
    public final static String MAP11_PATH = "src/main/resources/Maps/map10.txt";
    public final static String MAP12_PATH = "src/main/resources/Maps/map10.txt";
}