package org.example.arkanoid.config;

public class Constants {
    /*
    màn hình
     */
    public final static double SCREEN_WIDTH = 1260;
    public final static double SCREEN_HEIGHT = 800;
    public final static double DEFAULT_SCREEN_X = 110;
    public final static double DEFAULT_SCREEN_Y = 0;
    public final static String TITLE_SCREEN = "Arkanoid";

    /*
    FPS
     */
    public static final double FPS = 120.0;
    public static final double INTERVAL = 1000000000 / FPS; // nanoseconds per frame, that's 10^9/fps

    /*
    paddle
     */
    public final static double DEFAULT_PADDLE_WIDTH = 120;
    public final static double DEFAULT_PADDLE_HEIGHT = 30;
    public final static double DEFAULT_PADDLE_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_PADDLE_WIDTH / 2;
    public final static double DEFAULT_PADDLE_POSITION_Y = SCREEN_HEIGHT - 100;
    public final static double DEFAULT_PADDLE_DX = 0;
    public final static double DEFAULT_PADDLE_DY = 0;
    public final static double DEFAULT_PADDLE_SPEED = 4;

    /*
    ball
     */
    public final static double DEFAULT_BALL_SIZE = 15;
    public final static double DEFAULT_BALL_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_BALL_SIZE / 2;
    public final static double DEFAULT_BALL_POSITION_Y = DEFAULT_PADDLE_POSITION_Y - DEFAULT_BALL_SIZE - 5;
    public final static double DEFAULT_BALL_SPEED = 7;
    public final static double DEFAULT_BALL_OFFSET = 0.2;
    public final static double DEFAULT_BALL_OFFSET_CAP = 0.9;
    public final static double DEFAULT_BALL_DX = 0;
    public final static double DEFAULT_BALL_DY = -1;

    /*
    item
     */
    public final static double DEFAULT_ITEM_WIDTH = 30;
    public final static double DEFAULT_ITEM_HEIGHT = 30;
    public final static double DEFAULT_ITEM_DX = 0;
    public final static double DEFAULT_ITEM_DY = 2;

    public final static double DEFAULT_BULLET_WIDTH = 5;
    public final static double DEFAULT_BULLET_HEIGHT = 15;
    public final static double DEFAULT_BULLET_DX = 0;
    public final static double DEFAULT_BULLET_DY = -8;
    public final static double DEFAULT_BULLET_COOLDOWN = 100_000_000L;
    public final static double DEFAULT_SHOOTER_DURATION = 30_000_000_000L;



    /*
    hit point brick
     */
    public final static int HIT_POINTS_NORMAL_BRICK = 1;
    public final static int HIT_POINTS_STRONG_BRICK = 3;
    public final static int HIT_POINTS_INF_BRICK = Integer.MAX_VALUE;

    /*
    kích thước gạch
     */
    public final static double BRICK_WIDTH = 60;
    public final static double BRICK_HEIGHT = 30;

    /*
    số mạng ban đầu và điểm số mỗi khi phá được gạch
     */
    public final static int INITIAL_LIVES = 3;

    /*
    điểm số mỗi khi phá được gạch
     */
    public final static int POINTS_PER_BRICK = 10;

    /*
    tỉ lệ rơi đồ
     */
    public static final double DROP_CHANCE = 0.3;


    /*
    đường dẫn đến các file map
     */
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

    /*
    đường dẫn đến background các map
     */
    public final static String PATH_TO_BACKGROUND_MAP1  = "/Images/background/map1_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP2  = "/Images/background/map2_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP3  = "/Images/background/map3_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP4  = "/Images/background/map4_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP5  = "/Images/background/map5_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP6  = "/Images/background/map6_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP7  = "/Images/background/map7_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP8  = "/Images/background/map8_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP9  = "/Images/background/map9_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP10 = "/Images/background/map10_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP11 = "/Images/background/map11_background.jpg";
    public final static String PATH_TO_BACKGROUND_MAP12 = "/Images/background/map12_background.jpg";

    /*
    vùng chơi (play area)
     */
    public final static double PLAY_AREA_LEFT = 340;
    public final static double PLAY_AREA_RIGHT_MARGIN = 290;

    /*
    đường dẫn đến các file fxml
     */
    public final static String PATH_TO_GAME_VIEW    = "/org/example/arkanoid/game-view.fxml";
    public final static String PATH_TO_HELP_VIEW    = "/org/example/arkanoid/help-view.fxml";
    public final static String PATH_TO_LEVEL_VIEW   = "/org/example/arkanoid/level-view.fxml";
    public final static String PATH_TO_MAIN_MENU    = "/org/example/arkanoid/main-menu-view.fxml";
    public final static String PATH_TO_SETTING_VIEW = "/org/example/arkanoid/setting-view.fxml";

    /*
    đường dẫn đến hình ảnh chung
     */
    public final static String PATH_TO_LOGO = "/Images/logo/jarkanoid_logo.png";
    public final static String PATH_TO_BACKGROUND = "/Images/background/1.jpg";
    public final static String PATH_TO_MENU_BACKGROUND = "/Images/background/menu_Background.jpg";


    public static boolean isStarted = false;
    public static boolean isTransisioning = false;

    /*
    đường dẫn đến hình ảnh của brick
     */
    public final static String PATH_TO_BRICK_1 = "/Images/brick/1.png";
    public final static String PATH_TO_BRICK_2 = "/Images/brick/2.png";
    public final static String PATH_TO_BRICK_3 = "/Images/brick/3.png";
    public final static String PATH_TO_BRICK_4 = "/Images/brick/4.png";

    /*
    đường dẫn đến hình ảnh của paddle
     */
    public static final String PATH_TO_PADDLE_0 = "/Images/paddle/paddle0.png";
    public static final String PATH_TO_PADDLE_1= "/Images/paddle/paddle1.png";
    public static final String PATH_TO_PADDLE_2 = "/Images/paddle/paddle2.png";
    public static final String PATH_TO_SHOOTER_PADDLE = "/Images/paddle/shooter.png";
    public static final String PATH_TO_SHOOTS = "/Images/paddle/shoots.png";

    /*
    đường dẫn đến hình ảnh của ball
     */
    public static final String PATH_TO_BALL_1 = "/Images/ball/ball1.png";

    /*
    đường dẫn đến hình ảnh hiệu ứng đuôi của ball
     */
    public static final String PATH_TO_TRAIL_1 = "/Images/ball/trail_effect/trail_effect1.png";
    public static final String PATH_TO_TRAIL_2 = "/Images/ball/trail_effect/trail_effect2.png";

    /*
    đường dẫn đến hình ảnh của item
     */
    public static final String PATH_TO_SHRINK_1 = "/Images/item/Shrink/shrink1.png";
    public static final String PATH_TO_SHRINK_2 = "/Images/item/Shrink/shrink2.png";
    public static final String PATH_TO_SHRINK_3 = "/Images/item/Shrink/shrink3.png";
    public static final String PATH_TO_SHRINK_4 = "/Images/item/Shrink/shrink4.png";
    public static final String PATH_TO_SHRINK_5 = "/Images/item/Shrink/shrink5.png";
    public static final String PATH_TO_SHRINK_6 = "/Images/item/Shrink/shrink6.png";
    public static final String PATH_TO_SHRINK_7 = "/Images/item/Shrink/shrink7.png";
    public static final String PATH_TO_SHRINK_8 = "/Images/item/Shrink/shrink8.png";

    public static final String PATH_TO_EXPAND_1 = "/Images/item/Expand/expand1.png";
    public static final String PATH_TO_EXPAND_2 = "/Images/item/Expand/expand2.png";
    public static final String PATH_TO_EXPAND_3 = "/Images/item/Expand/expand3.png";
    public static final String PATH_TO_EXPAND_4 = "/Images/item/Expand/expand4.png";
    public static final String PATH_TO_EXPAND_5 = "/Images/item/Expand/expand5.png";
    public static final String PATH_TO_EXPAND_6 = "/Images/item/Expand/expand6.png";
    public static final String PATH_TO_EXPAND_7 = "/Images/item/Expand/expand7.png";
    public static final String PATH_TO_EXPAND_8 = "/Images/item/Expand/expand8.png";

    public static final String PATH_TO_EXTRA_LIFE = "/Images/item/extra_heart.png";

    public static final String PATH_TO_TRANSFER_SHOOTER = "/Images/item/transfer_shooter.png";
}
