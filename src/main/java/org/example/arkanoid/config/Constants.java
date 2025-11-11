package org.example.arkanoid.config;

import javafx.scene.image.Image;
import org.example.arkanoid.game.ProgressManager;

import java.util.HashMap;
import java.util.Map;

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
    public static final double FPS = 60.0;

    /*
    paddle
     */
    public final static double DEFAULT_PADDLE_WIDTH = 120;
    public final static double DEFAULT_PADDLE_HEIGHT = 30;
    public final static double DEFAULT_PADDLE_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_PADDLE_WIDTH / 2;
    public final static double DEFAULT_PADDLE_POSITION_Y = SCREEN_HEIGHT - DEFAULT_PADDLE_HEIGHT - 10;
    public final static double DEFAULT_PADDLE_DX = 0;
    public final static double DEFAULT_PADDLE_DY = 0;
    public final static double DEFAULT_PADDLE_SPEED = 7;

    /*
    ball
     */
    public final static double DEFAULT_BALL_SIZE = 20;
    public final static double DEFAULT_BALL_POSITION_X = SCREEN_WIDTH / 2 - DEFAULT_BALL_SIZE / 2;
    public final static double DEFAULT_BALL_POSITION_Y = DEFAULT_PADDLE_POSITION_Y - DEFAULT_BALL_SIZE - 5;
    public final static double DEFAULT_BALL_SPEED = 5;
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

    public final static double DEFAULT_BULLET_WIDTH = 8;
    public final static double DEFAULT_BULLET_HEIGHT = 15;
    public final static double DEFAULT_BULLET_DX = 0;
    public final static double DEFAULT_BULLET_DY = -8;

    /*
    thời gian hoạt động của item
     */
    public final static double DEFAULT_BULLET_COOLDOWN = 100_000_000L;
    public final static double DEFAULT_SHOOTER_DURATION = 10_000_000_000.0;
    public final static double DEFAULT_SAFETY_NET_DURATION = 10_000_000_000.0;
    public final static double DEFAULT_SIZE_DURATION = 10_000_000_000.0;
    public final static double DEFAULT_SPEED_DURATION = 10_000_000_000.0;

    /*
    hit point brick
     */
    public final static int HIT_POINTS_NORMAL_BRICK = 1;
    public final static int HIT_POINTS_STRONG_BRICK = 3;
    public final static int HIT_POINTS_INF_BRICK = Integer.MAX_VALUE;

    /*
    vùng chơi (play area)
    */
    public final static double PLAY_AREA_LEFT = 318;
    public final static double PLAY_AREA_RIGHT_MARGIN = 327;


    /*
    kích thước gạch
     */
    public final static double BRICK_WIDTH =  29;
    public final static double BRICK_HEIGHT = BRICK_WIDTH;

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
    public final static String[] MAP_PATH  = new String[13];

    /*
    đường dẫn đến các file fxml
     */
    public final static String PATH_TO_GAME_VIEW    = "/org/example/arkanoid/game-view.fxml";
    public final static String PATH_TO_SCORE_VIEW    = "/org/example/arkanoid/scoreboard-view.fxml";
    public final static String PATH_TO_HELP_VIEW    = "/org/example/arkanoid/help-view.fxml";
    public final static String PATH_TO_LEVEL_VIEW   = "/org/example/arkanoid/level-view.fxml";
    public final static String PATH_TO_MAIN_MENU    = "/org/example/arkanoid/main-menu-view.fxml";
    public final static String PATH_TO_SETTING_VIEW = "/org/example/arkanoid/setting-view.fxml";
    public final static String PATH_TO_LOGIN_VIEW   = "/org/example/arkanoid/login-view.fxml";
    public final static String PATH_TO_SHOP_VIEW   = "/org/example/arkanoid/shop-view.fxml";

    /*
    đường dẫn đến hình ảnh chung
     */
    public final static String PATH_TO_LOGO = "/Images/logo/logo.png";
    public final static String PATH_TO_GAME_VIDEO = "/Images/background/main_video_background.mp4";
    public final static String PATH_TO_GAME_BACKGROUND = "/Images/background/main_game_background.jpg";
    public final static String PATH_TO_MENU_BACKGROUND = "/Images/background/menu_Background.jpg";
    public final static String PATH_TO_IMAGE_BACKGROUND = "/Images/background/main_image_background.jpg";
    public final static String PATH_TO_MAIN_MENU_VIDEO = "/Images/background/video_main_menu.mp4";
    public static boolean isStarted = false;

    /*
    đường dẫn đến hình ảnh của brick
     */

    /*
    Gạch vỡ (Broken Bricks)
     */
    public final static String PATH_TO_BROKEN_BRICK1 = "/Images/brick/brokenbrick1.png";
    public final static String PATH_TO_BROKEN_BRICK2 = "/Images/brick/brokenbrick2.png";
    public final static String PATH_TO_BROKEN_BRICK3 = "/Images/brick/brokenbrick3.png";
    public final static String PATH_TO_BROKEN_BRICK3_2 = "/Images/brick/brokenbrick3-2.png";
    public final static String PATH_TO_BROKEN_BRICK4 = "/Images/brick/brokenbrick4.png";
    public final static String PATH_TO_BROKEN_BRICK5 = "/Images/brick/brokenbrick5.png";
    public final static String PATH_TO_BROKEN_BRICK6 = "/Images/brick/brokenbrick6.png";
    public final static String PATH_TO_BROKEN_BRICK7 = "/Images/brick/brokenbrick7.png";
    public final static String PATH_TO_BROKEN_BRICK8 = "/Images/brick/brokenbrick8.png";
    public final static String PATH_TO_BROKEN_BRICK9 = "/Images/brick/brokenbrick9.png";
    public final static String PATH_TO_BROKEN_BRICK10 = "/Images/brick/brokenbrick10.png";
    public final static String PATH_TO_BROKEN_BRICK11 = "/Images/brick/brokenbrick11.png";
    public final static String PATH_TO_BROKEN_BRICK12 = "/Images/brick/brokenbrick12.png";
    public final static String PATH_TO_BROKEN_BRICK13 = "/Images/brick/brokenbrick13.png";
    public final static String PATH_TO_BROKEN_BRICK14 = "/Images/brick/brokenbrick14.png";

    /*
    Gạch thường (Normal Bricks)
     */
    public final static String PATH_TO_NORMAL_BRICK1 = "/Images/brick/normalbrick1.png";
    public final static String PATH_TO_NORMAL_BRICK2 = "/Images/brick/normalbrick2.png";
    public final static String PATH_TO_NORMAL_BRICK3 = "/Images/brick/normalbrick12.png";
    public final static String PATH_TO_NORMAL_BRICK4 = "/Images/brick/normalbrick4.png";
    public final static String PATH_TO_NORMAL_BRICK5 = "/Images/brick/normalbrick5.png";
    public final static String PATH_TO_NORMAL_BRICK6 = "/Images/brick/normalbrick6.png";
    public final static String PATH_TO_NORMAL_BRICK7 = "/Images/brick/normalbrick7.png";
    public final static String PATH_TO_NORMAL_BRICK8 = "/Images/brick/normalbrick8.png";
    public final static String PATH_TO_NORMAL_BRICK9 = "/Images/brick/normalbrick9.png";
    public final static String PATH_TO_NORMAL_BRICK10 = "/Images/brick/normalbrick10.png";
    public final static String PATH_TO_NORMAL_BRICK11 = "/Images/brick/normalbrick11.png";
    public final static String PATH_TO_NORMAL_BRICK13 = "/Images/brick/normalbrick13.png";
    public final static String PATH_TO_NORMAL_BRICK14 = "/Images/brick/normalbrick14.png";
    public final static String PATH_TO_NORMAL_BRICKKK = "/Images/brick/normalbrick3.png";



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
    public static final String PATH_TO_BALL_PANCAKE = "/Images/ball/Pancake.png";
    public static final String PATH_TO_BALL_EARTH = "/Images/Ball/Earth.png";
    public static final String PATH_TO_BALL_ENDERMAN1 = "/Images/Ball/Enderman1.png";
    public static final String PATH_TO_BALL_ENDERMAN2 = "/Images/Ball/Enderman2.png";
    public static final String PATH_TO_BALL_CHROME = "/Images/Ball/Chrome.png";
    public static final String PATH_TO_BALL_SOCCER = "/Images/Ball/Soccer.png";


    /*
    đường dẫn đến hình ảnh hiệu ứng đuôi của ball
     */
    public static final String PATH_TO_TRAIL_LGBT = "/Images/trail/LGBT.png";
    public static final String PATH_TO_TRAIL_Lightning = "/Images/trail/Lightning.png";
    public static final String PATH_TO_TRAIL_DOLLA = "/Images/trail/Dolla.png";
    public static final String PATH_TO_TRAIL_DIAMOND = "/Images/trail/Diamond.png";
    public static final String PATH_TO_TRAIL_PRIMOGEM = "/Images/trail/Primogem.png";
    public static final String PATH_TO_TRAIL_LUCKYCLOVER = "/Images/trail/LuckyClover.png";

    // --- ID Vật Phẩm ---
    public static final String BALL_SKIN_PANCAKE = "BALL_PANCAKE";
    public static final String BALL_SKIN_EARTH = "BALL_EARTH";
    public static final String BALL_SKIN_ENDERMAN = "BALL_ENDERMAN"; // (Giữ lại ID này nếu file save cũ dùng nó)
    public static final String BALL_SKIN_ENDERMAN1 = "BALL_ENDERMAN1"; // (Sửa trong FXML)
    public static final String BALL_SKIN_ENDERMAN2 = "BALL_ENDERMAN2";
    public static final String BALL_SKIN_CHROME = "BALL_CHROME";
    public static final String BALL_SKIN_SOCCER = "BALL_SOCCER";


    public static final String TRAIL_SKIN_LGBT = "TRAIL_LGBT"; // Mặc định
    public static final String TRAIL_SKIN_LIGHTNING = "TRAIL_LIGHTNING";
    public static final String TRAIL_SKIN_DOLLA = "TRAIL_DOLLA";
    public static final String TRAIL_SKIN_DIAMOND = "TRAIL_DIAMOND";
    public static final String TRAIL_SKIN_PRIMOGEM = "TRAIL_PRIMOGEM";
    public static final String TRAIL_SKIN_LUCKYCLOVER = "TRAIL_LUCKYCLOVER";

    // *** THÊM MỚI: ID cho Paddle ***
    public static final String PADDLE_SKIN_DEFAULT = "PADDLE_SKIN_DEFAULT";
    public static final String PADDLE_SKIN_2 = "PADDLE_SKIN2";


    // --- Giá Vật Phẩm ---
    public static final Map<String, Integer> ITEM_COSTS = new HashMap<>();
    static {
        // Giá Ball (dựa trên FXML mới)
        ITEM_COSTS.put(BALL_SKIN_EARTH, 0);
        ITEM_COSTS.put(BALL_SKIN_PANCAKE, 2000);
        // *** SỬA: Đảm bảo dùng đúng ID từ FXML ***
        ITEM_COSTS.put(BALL_SKIN_ENDERMAN1, 3000); // (Enderman1)
        ITEM_COSTS.put(BALL_SKIN_ENDERMAN, 3000);  // (Hỗ trợ ID cũ BALL_ENDERMAN)
        ITEM_COSTS.put(BALL_SKIN_SOCCER, 4000);
        ITEM_COSTS.put(BALL_SKIN_ENDERMAN2, 5000);
        ITEM_COSTS.put(BALL_SKIN_CHROME, 6000);

        // Giá Trail (dựa trên FXML)
        ITEM_COSTS.put(TRAIL_SKIN_LGBT, 0);
        ITEM_COSTS.put(TRAIL_SKIN_LIGHTNING, 2000);
        ITEM_COSTS.put(TRAIL_SKIN_DOLLA, 3000);
        ITEM_COSTS.put(TRAIL_SKIN_DIAMOND, 4000);
        ITEM_COSTS.put(TRAIL_SKIN_PRIMOGEM, 5000);
        ITEM_COSTS.put(TRAIL_SKIN_LUCKYCLOVER, 6000);

        // Giá Paddle (dựa trên FXML)
        ITEM_COSTS.put(PADDLE_SKIN_DEFAULT, 0);
        ITEM_COSTS.put(PADDLE_SKIN_2, 2000);
    }

    public static final Map<String, String> BALL_SKIN_PATHS = new HashMap<>();
    public static final Map<String, String> TRAIL_SKIN_PATHS = new HashMap<>();
    public static final Map<String, String> PADDLE_SKIN_PATHS = new HashMap<>();


    static {
        BALL_SKIN_PATHS.put(BALL_SKIN_PANCAKE, Constants.PATH_TO_BALL_PANCAKE);
        BALL_SKIN_PATHS.put(BALL_SKIN_EARTH, Constants.PATH_TO_BALL_EARTH);
        BALL_SKIN_PATHS.put(BALL_SKIN_ENDERMAN, Constants.PATH_TO_BALL_ENDERMAN1); // ID cũ trỏ về ảnh 1
        BALL_SKIN_PATHS.put(BALL_SKIN_ENDERMAN1, Constants.PATH_TO_BALL_ENDERMAN1); // ID mới trỏ về ảnh 1
        BALL_SKIN_PATHS.put(BALL_SKIN_ENDERMAN2, Constants.PATH_TO_BALL_ENDERMAN2); // ID mới trỏ về ảnh 2
        BALL_SKIN_PATHS.put(BALL_SKIN_CHROME, Constants.PATH_TO_BALL_CHROME);
        BALL_SKIN_PATHS.put(BALL_SKIN_SOCCER, Constants.PATH_TO_BALL_SOCCER);

        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_LGBT, Constants.PATH_TO_TRAIL_LGBT);
        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_LIGHTNING, Constants.PATH_TO_TRAIL_Lightning);
        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_DOLLA, Constants.PATH_TO_TRAIL_DOLLA);
        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_DIAMOND, Constants.PATH_TO_TRAIL_DIAMOND);
        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_PRIMOGEM, Constants.PATH_TO_TRAIL_PRIMOGEM);
        TRAIL_SKIN_PATHS.put(TRAIL_SKIN_LUCKYCLOVER, Constants.PATH_TO_TRAIL_LUCKYCLOVER);

        PADDLE_SKIN_PATHS.put(PADDLE_SKIN_DEFAULT, Constants.PATH_TO_PADDLE_0);
        PADDLE_SKIN_PATHS.put(PADDLE_SKIN_2, Constants.PATH_TO_PADDLE_1);
    }



    // (Đây là các giá trị mặc định khi bắt đầu game)
    public static String CURRENTLY_EQUIPPED_BALL = PATH_TO_BALL_EARTH;
    public static String CURRENTLY_EQUIPPED_TRAIL = PATH_TO_TRAIL_LGBT;
    // *** THÊM MỚI: Mặc định cho Paddle ***
    public static String CURRENTLY_EQUIPPED_PADDLE = PATH_TO_PADDLE_0;


    public final static String PATH_TO_CURSOR = "/Images/background/cursor1.png";
    public final static String PATH_TO_HOVER_CURSOR = "/Images/background/hover_cursor.png";

    /*
    đường dẫn đến hình ảnh của item
     */
    public static final String[] PATH_TO_SHRINK_ANIM = {
            "/Images/item/Shrink/powerup_shrink_1.png",
            "/Images/item/Shrink/powerup_shrink_2.png",
            "/Images/item/Shrink/powerup_shrink_3.png",
            "/Images/item/Shrink/powerup_shrink_4.png",
            "/Images/item/Shrink/powerup_shrink_5.png",
            "/Images/item/Shrink/powerup_shrink_6.png",
            "/Images/item/Shrink/powerup_shrink_7.png",
            "/Images/item/Shrink/powerup_shrink_8.png"
    };

    public static final String[] PATH_TO_EXPAND_ANIM = {
            "/Images/item/Expand/powerup_expand_1.png",
            "/Images/item/Expand/powerup_expand_2.png",
            "/Images/item/Expand/powerup_expand_3.png",
            "/Images/item/Expand/powerup_expand_4.png",
            "/Images/item/Expand/powerup_expand_5.png",
            "/Images/item/Expand/powerup_expand_6.png",
            "/Images/item/Expand/powerup_expand_7.png",
            "/Images/item/Expand/powerup_expand_8.png"
    };


    public static final String PATH_TO_BRICK_3333 = "/Images/background/3333.jpg";
    public static Image[] brick_state_list1 = new Image[10];
    public static Image[] brick_state_list2 = new Image[10];

    public static final String PATH_TO_EXTRA_LIFE = "/Images/item/extra_heart.png";

    public static final String PATH_TO_TRANSFER_SHOOTER = "/Images/item/transfer_shooter.png";

    /*
    đường dẫn đến âm thanh
     */
    public static final String PATH_TO_SOUND_BACKGROUND_1 = "/Sounds/background1.mp3";
    public static final String PATH_TO_SOUND_BACKGROUND_2 = "/Sounds/background2.mp3";
    public static final String PATH_TO_SOUND_BACKGROUND_3 = "/Sounds/background3.mp3";

    public static final String PATH_TO_SOUND_HOVER = "/Sounds/hover1.mp3";
    public static final String PATH_TO_SOUND_PAUSE = "/Sounds/click.mp3";
    public static final String PATH_TO_SOUND_UNPAUSE = "/Sounds/click.mp3";
    public static final String PATH_TO_SOUND_USE_ITEM = "/Sounds/Use_item1.mp3";

    public static final String PATH_TO_SOUND_CLICK = "/Sounds/click.mp3";
    public static final String PATH_TO_SOUND_BALL_OUT = "/Sounds/ballOut.mp3";

    public static final String PATH_TO_SOUND_SHOOT = "/Sounds/gun1.mp3";

    public static final String PATH_TO_SOUND_WIN = "/Sounds/win.wav";
    public static final String PATH_TO_SOUND_LOSE = "/Sounds/lose.mp3";
    public static final String PATH_TO_SOUND_AFTERWIN = "/Sounds/afterwin.mp3";
    public static final String PATH_TO_SOUND_AFTERLOSE = "/Sounds/afterlose.mp3";

    public static final String PATH_TO_SOUND_PADDLE_HIT = "/Sounds/paddleHit.mp3";
    public static final String PATH_TO_SOUND_WALL_HIT = "/Sounds/wallHit.mp3";
    public static final String PATH_TO_SOUND_BRICK_3 ="/Sounds/brick_3.mp3";

    public static final String PATH_TO_SOUND_GUN_ITEM = "/Sounds/gunItem.wav";
    public static final String PATH_TO_SOUND_GUN_LOAD = "/Sounds/gunLoad.mp3";

    // Các giá trị final cho từng chế độ
    public final static int EASY_LIVES = 5;
    public final static double EASY_BALL_SPEED = 4;
    public final static double EASY_PADDLE_SPEED = 7;
    public final static double EASY_DROP_CHANCE = 0.5; // 50%

    public final static int NORMAL_LIVES = 3;
    public final static double NORMAL_BALL_SPEED = 5;
    public final static double NORMAL_PADDLE_SPEED = 8;
    public final static double NORMAL_DROP_CHANCE = 0.3; // 30%

    public final static int HARD_LIVES = 2;
    public final static double HARD_BALL_SPEED = 7;
    public final static double HARD_PADDLE_SPEED = 10;
    public final static double HARD_DROP_CHANCE = 0.15; // 15%

    // Các biến (không final) để lưu cài đặt HIỆN TẠI
    public static int CURRENT_LIVES = NORMAL_LIVES;
    public static double CURRENT_BALL_SPEED = NORMAL_BALL_SPEED;
    public static double CURRENT_PADDLE_SPEED = NORMAL_PADDLE_SPEED;
    public static double CURRENT_DROP_CHANCE = NORMAL_DROP_CHANCE;
    public static String CURRENT_DIFFICULTY = "Thường";
    public static int CURRENT_DIFFICULTY_SETTING = ProgressManager.DIFFICULTY_NORMAL;

    public static void setDifficulty(int difficultySetting) {
        if (difficultySetting == ProgressManager.DIFFICULTY_EASY) { // 0
            CURRENT_LIVES = EASY_LIVES;
            CURRENT_BALL_SPEED = EASY_BALL_SPEED;
            CURRENT_PADDLE_SPEED = EASY_PADDLE_SPEED;
            CURRENT_DROP_CHANCE = EASY_DROP_CHANCE;
            CURRENT_DIFFICULTY = "Dễ";
            CURRENT_DIFFICULTY_SETTING = ProgressManager.DIFFICULTY_EASY;
        } else if (difficultySetting == ProgressManager.DIFFICULTY_HARD) { // 2
            CURRENT_LIVES = HARD_LIVES;
            CURRENT_BALL_SPEED = HARD_BALL_SPEED;
            CURRENT_PADDLE_SPEED = HARD_PADDLE_SPEED;
            CURRENT_DROP_CHANCE = HARD_DROP_CHANCE;
            CURRENT_DIFFICULTY = "Khó";
            CURRENT_DIFFICULTY_SETTING = ProgressManager.DIFFICULTY_HARD;
        } else { // 1 (Normal) là mặc định
            CURRENT_LIVES = NORMAL_LIVES;
            CURRENT_BALL_SPEED = NORMAL_BALL_SPEED;
            CURRENT_PADDLE_SPEED = NORMAL_PADDLE_SPEED;
            CURRENT_DROP_CHANCE = NORMAL_DROP_CHANCE;
            CURRENT_DIFFICULTY = "Thường";
            CURRENT_DIFFICULTY_SETTING = ProgressManager.DIFFICULTY_NORMAL;
        }
        System.out.println("Đã đặt độ khó: " + CURRENT_DIFFICULTY + " (Mạng: " + CURRENT_LIVES + ")");
    }

    public static final String[] PATH_TO_EXPLOSION_ANIM = {
            "/Images/Explosion/1.png",
            "/Images/Explosion/2.png",
            "/Images/Explosion/3.png",
            "/Images/Explosion/4.png",
            "/Images/Explosion/5.png",
            "/Images/Explosion/6.png",
            "/Images/Explosion/7.png",
            "/Images/Explosion/8.png",
            "/Images/Explosion/9.png",
            "/Images/Explosion/10.png",
            "/Images/Explosion/11.png",
            "/Images/Explosion/12.png",
            "/Images/Explosion/13.png",
            "/Images/Explosion/14.png",
            "/Images/Explosion/15.png",
            "/Images/Explosion/16.png",
            "/Images/Explosion/17.png",
            "/Images/Explosion/18.png",
            "/Images/Explosion/19.png",
            "/Images/Explosion/20.png",
            "/Images/Explosion/21.png",
            "/Images/Explosion/22.png",
            "/Images/Explosion/23.png",
            "/Images/Explosion/24.png",
            "/Images/Explosion/25.png"
    };

    public static final String PATH_TO_INF_BRICK_STATIC = "/Images/brick/brick_silver.png";
    public static final String[] PATH_TO_INF_BRICK_ANIM = {
            "/Images/brick/brick_silver_1.png",
            "/Images/brick/brick_silver_2.png",
            "/Images/brick/brick_silver_3.png",
            "/Images/brick/brick_silver_4.png",
            "/Images/brick/brick_silver_5.png",
            "/Images/brick/brick_silver_6.png",
            "/Images/brick/brick_silver_7.png",
            "/Images/brick/brick_silver_8.png",
            "/Images/brick/brick_silver_9.png",
            "/Images/brick/brick_silver_10.png"
    };

    public static final String[] PATH_TO_MULTI_BALL_ANIM = {
            "/Images/item/MultiBall/enemy_molecule_1.png",
            "/Images/item/MultiBall/enemy_molecule_2.png",
            "/Images/item/MultiBall/enemy_molecule_3.png",
            "/Images/item/MultiBall/enemy_molecule_4.png",
            "/Images/item/MultiBall/enemy_molecule_5.png",
            "/Images/item/MultiBall/enemy_molecule_6.png",
            "/Images/item/MultiBall/enemy_molecule_7.png",
            "/Images/item/MultiBall/enemy_molecule_8.png",
            "/Images/item/MultiBall/enemy_molecule_9.png",
            "/Images/item/MultiBall/enemy_molecule_10.png",
            "/Images/item/MultiBall/enemy_molecule_11.png",
            "/Images/item/MultiBall/enemy_molecule_12.png",
            "/Images/item/MultiBall/enemy_molecule_13.png",
            "/Images/item/MultiBall/enemy_molecule_14.png",
            "/Images/item/MultiBall/enemy_molecule_15.png",
            "/Images/item/MultiBall/enemy_molecule_16.png",
            "/Images/item/MultiBall/enemy_molecule_17.png",
            "/Images/item/MultiBall/enemy_molecule_18.png",
            "/Images/item/MultiBall/enemy_molecule_19.png",
            "/Images/item/MultiBall/enemy_molecule_20.png",
            "/Images/item/MultiBall/enemy_molecule_21.png",
            "/Images/item/MultiBall/enemy_molecule_22.png",
            "/Images/item/MultiBall/enemy_molecule_23.png",
            "/Images/item/MultiBall/enemy_molecule_24.png",
            "/Images/item/MultiBall/enemy_molecule_25.png"
    };

    public static final String[] PATH_TO_SAFETY_ANIM = {
            "/Images/item/Safety/enemy_cone_1.png",
            "/Images/item/Safety/enemy_cone_2.png",
            "/Images/item/Safety/enemy_cone_3.png",
            "/Images/item/Safety/enemy_cone_4.png",
            "/Images/item/Safety/enemy_cone_5.png",
            "/Images/item/Safety/enemy_cone_6.png",
            "/Images/item/Safety/enemy_cone_7.png",
            "/Images/item/Safety/enemy_cone_8.png",
            "/Images/item/Safety/enemy_cone_9.png",
            "/Images/item/Safety/enemy_cone_10.png",
            "/Images/item/Safety/enemy_cone_11.png",
            "/Images/item/Safety/enemy_cone_12.png",
            "/Images/item/Safety/enemy_cone_13.png",
            "/Images/item/Safety/enemy_cone_14.png",
            "/Images/item/Safety/enemy_cone_15.png",
            "/Images/item/Safety/enemy_cone_16.png",
            "/Images/item/Safety/enemy_cone_17.png",
            "/Images/item/Safety/enemy_cone_18.png",
            "/Images/item/Safety/enemy_cone_19.png",
            "/Images/item/Safety/enemy_cone_20.png",
            "/Images/item/Safety/enemy_cone_21.png",
            "/Images/item/Safety/enemy_cone_22.png",
            "/Images/item/Safety/enemy_cone_23.png",
            "/Images/item/Safety/enemy_cone_24.png",
            "/Images/item/Safety/enemy_cone_25.png"
    };

    public static final String[] PATH_TO_PADDLE_PULSATE_ANIM = {
            "/Images/paddle/paddle_pulsate_1.png",
            "/Images/paddle/paddle_pulsate_2.png",
            "/Images/paddle/paddle_pulsate_3.png"
    };

    public static final String[] PATH_TO_SHOOTER_PULSATE_ANIM = {
            "/Images/paddle/paddle_shooter_pulsate_1.png",
            "/Images/paddle/paddle_shooter_pulsate_2.png",
            "/Images/paddle/paddle_shooter_pulsate_3.png"
    };

    public static final String[] PATH_TO_PADDLE_MATERIALIZE_ANIM = {
            "/Images/paddle/paddle_materialize_1.png",
            "/Images/paddle/paddle_materialize_2.png",
            "/Images/paddle/paddle_materialize_3.png",
            "/Images/paddle/paddle_materialize_4.png",
            "/Images/paddle/paddle_materialize_5.png",
            "/Images/paddle/paddle_materialize_6.png",
            "/Images/paddle/paddle_materialize_7.png",
            "/Images/paddle/paddle_materialize_8.png",
            "/Images/paddle/paddle_materialize_9.png",
            "/Images/paddle/paddle_materialize_10.png",
            "/Images/paddle/paddle_materialize_11.png",
            "/Images/paddle/paddle_materialize_12.png",
            "/Images/paddle/paddle_materialize_13.png",
            "/Images/paddle/paddle_materialize_14.png",
            "/Images/paddle/paddle_materialize_15.png"
    };

    public static final String[] PATH_TO_SHOOTER_ITEM_ANIM = {
            "/Images/item/shooter/powerup_laser_1.png",
            "/Images/item/shooter/powerup_laser_2.png",
            "/Images/item/shooter/powerup_laser_3.png",
            "/Images/item/shooter/powerup_laser_4.png",
            "/Images/item/shooter/powerup_laser_5.png",
            "/Images/item/shooter/powerup_laser_6.png",
            "/Images/item/shooter/powerup_laser_7.png",
            "/Images/item/shooter/powerup_laser_8.png"
    };

    public static final String[] PATH_TO_SLOW_ANIM = {
            "/Images/item/Slow/powerup_slow_1.png",
            "/Images/item/Slow/powerup_slow_2.png",
            "/Images/item/Slow/powerup_slow_3.png",
            "/Images/item/Slow/powerup_slow_4.png",
            "/Images/item/Slow/powerup_slow_5.png",
            "/Images/item/Slow/powerup_slow_6.png",
            "/Images/item/Slow/powerup_slow_7.png",
            "/Images/item/Slow/powerup_slow_8.png"
    };

    public static final String PATH_TO_SOUND_EXPLOSION = "/Sounds/explosion.wav";

    public static final String PATH_TO_SOUND_NEGATIVE_BUFF = "/Sounds/negative_buff.mp3";

    public final static String PATH_TO_COIN_50 = "/Images/Coin/extra50.png";
    public final static String PATH_TO_COIN_100 = "/Images/Coin/extra100.png";
    public final static String PATH_TO_COIN_250 = "/Images/Coin/extra250.png";
    public final static String PATH_TO_COIN_500 = "/Images/Coin/extra500.png";

    public final static String PATH_TO_SOUND_BUY= "/Sounds/Buy.wav";
    public final static String PATH_TO_SOUND_DENIED = "/Sounds/Denied.wav";
    public final static String PATH_TO_SOUND_EQUIP = "/Sounds/Equip.wav";
}
