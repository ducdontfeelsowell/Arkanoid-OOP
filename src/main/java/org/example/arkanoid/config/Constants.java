package org.example.arkanoid.config;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Constants {
    // TODO: sửa lại các hằng số để vừa mắt

    // Ví dụ sửa thành 1280x720
    public final static double SCREEN_WIDTH = 1260;
    public final static double SCREEN_HEIGHT = 800;
    public final static double DEFAULT_SCREEN_X = 110;
    public final static double DEFAULT_SCREEN_Y = 0;
    public final static String TITLE_SCREEN = "Arkanoid";

    public final static double DEFAULT_PADDLE_WIDTH = 120;
    public final static double DEFAULT_PADDLE_HEIGHT = 50;
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
    public final static double DEFAULT_BALL_SPEED = 8;
    public final static int DEFAULT_BALL_DIRECTION_X = 1;
    public final static int DEFAULT_BALL_DIRECTION_Y = -1;

    // hit points brick
    public final static int HIT_POINTS_NORMAL_BRICK = 1;
    public final static int HIT_POINTS_STRONG_BRICK = 3;
    public final static int HIT_POINTS_INF_BRICK = Integer.MAX_VALUE;

    public final static double BRICK_WIDTH = 60;
    public final static double BRICK_HEIGHT = 30;

    public final static int INITIAL_LIVES = 3;
    public final static int POINTS_PER_BRICK = 10;

    // đường dẫn đến các map
    public final static String MAP1_PATH  = "src/main/resources/Maps/map1.txt";
    public final static String MAP2_PATH  = "src/main/resources/Maps/map2.txt";
    public final static String MAP3_PATH  = "src/main/resources/Maps/map3.txt";
    public final static String MAP4_PATH  = "src/main/resources/Maps/map4.txt";
    public final static String MAP5_PATH  = "src/main/resources/Maps/map5.txt";
    public final static String MAP6_PATH  = "src/main/resources/Maps/map6.txt";
    public final static String MAP7_PATH  = "src/main/resources/Maps/map7.txt";
    public final static String MAP8_PATH  = "src/main/resources/Maps/map8.txt";
    public final static String MAP9_PATH  = "src/main/resources/Maps/map9.txt";
    public final static String MAP10_PATH = "src/main/resources/Maps/map10.txt";
    public final static String MAP11_PATH = "src/main/resources/Maps/map11.txt";
    public final static String MAP12_PATH = "src/main/resources/Maps/map12.txt";

    // đường dẫn đến các scene
    public final static String PATH_TO_GAME_VIEW    = "/org/example/arkanoid/game-view.fxml";
    public final static String PATH_TO_HELP_VIEW    = "/org/example/arkanoid/help-view.fxml";
    public final static String PATH_TO_LEVEL_VIEW   = "/org/example/arkanoid/level-view.fxml";
    public final static String PATH_TO_MAIN_MENU    = "/org/example/arkanoid/main-menu-view.fxml";
    public final static String PATH_TO_SETTING_VIEW = "/org/example/arkanoid/setting-view.fxml";

    // đường dẫn đến hình ảnh
    public final static String PATH_TO_LOGO = "/Images/logo/jarkanoid_logo.png";
    public final static String PATH_TO_VIDEO = "/Images/background/video2.mp4";
    public final static String PATH_TO_BACKGROUND = "/Images/background/1.jpg";
    public final static String PATH_TO_MENU_BACKGROUND = "/Images/background/menu_Background.jpg";


    public static boolean isStarted = false;

    // đường dẫn đến hình ảnh của gạch
    public final static String PATH_TO_BRICK_1 = "/Images/brick/1.png";
    public final static String PATH_TO_BRICK_2 = "/Images/brick/2.png";
    public final static String PATH_TO_BRICK_3 = "/Images/brick/3.png";
    public final static String PATH_TO_BRICK_4 = "/Images/brick/4.png";

    // đường dẫn đến hình ảnh của ball
    public static final String PATH_TO_BALL_1 = "";

    // đường dẫn đến hình ảnh của trail
    public static final String PATH_TO_TRAIL_1 = "/Images/ball/trail_effect/trail_effect1.png";
    public static final String PATH_TO_TRAIL_2 = "Images/ball/trail_effect/trail_effect2.png";


    // đường dẫn đến hình ảnh của paddle
    public static final String PATH_TO_PADDLE_1 = "/Images/paddle/paddle1.png";
    public static final String PATH_TO_PADDLE_2 = "/Images/paddle/paddle2.png";

    public static Image[] brick_state_list1 = new Image[10];
    public static Image[] brick_state_list2 = new Image[10];

}