package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Node;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Cursor; // Đã thêm
import javafx.scene.Scene; // Đã thêm
import javafx.scene.image.Image; // Đã thêm

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    // THÊM: Biến Cursor tĩnh
    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    public Button resumeGameButton;
    public Button backButton1;
    public Button playAgainButton;
    public Button backButton2;
    public Button backButton3;

    // Khai báo nút Next Level
    public Button nextLevelButton;

    public AnchorPane loseScreen;
    public AnchorPane pauseScreen;
    public AnchorPane winScreen;

    public static boolean paused = false;
    private boolean escapeWasPressed = false;

    private InputHandler inputHandler;

    @FXML
    private MediaView win_backgroundMediaView;

    @FXML
    private MediaView lose_backgroundMediaView;


    @FXML
    private ImageView backHoverImage1; // Back Button 1 (Pause Screen)

    @FXML
    private ImageView backHoverImage2; // Back Button 2 (Lose Screen)

    @FXML
    private ImageView backHoverImage3; // Back Button 3 (Win Screen)

    @FXML
    private ImageView resumeHoverImage1; // Resume Button (Pause Screen)

    @FXML
    private ImageView playAgainHoverImage; // Play Again Button (Lose Screen)

    // Khai báo ImageView cho nút Next Level
    @FXML
    private ImageView nextLevelHoverImage;

    private MediaPlayer win_mediaPlayer;

    private MediaPlayer lose_mediaPlayer;

    public void update() {
        if (inputHandler != null) {
            boolean escapePressed = inputHandler.isEscapePressed();

            // Detect escape key press (edge detection)
            if (escapePressed && !escapeWasPressed) {
                togglePause();
            }

            escapeWasPressed = escapePressed;
        }
    }

    private void togglePause() {
        paused = !paused;
        pauseScreen.setVisible(paused);

        if (paused) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PAUSE);
            SoundManager.getInstance().pauseBackgroundMusic();
        } else {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
            SoundManager.getInstance().resumeBackgroundMusic();
        }
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public static boolean isPaused() {
        return paused;
    }

    // THÊM: Phương thức khởi tạo Cursor
    private void initializeCursors(Scene scene) {
        // Khởi tạo con trỏ mặc định

            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    System.out.println("GGGGGGGGGGGGGG");
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    // Áp dụng con trỏ mặc định cho Scene
                    if (scene != null) { // Đảm bảo scene không null
                        scene.setCursor(defaultGameCursor);
                    }
                } else {
                    System.out.println("MMMMMMMMMMMM");
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong GameController: " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }

        // Khởi tạo con trỏ hover
        if (buttonHoverCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_HOVER_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    buttonHoverCursor = Cursor.cursor(cursorUrl.toExternalForm());
                } else {
                    buttonHoverCursor = Cursor.HAND;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong GameController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }

    private void addHoverEffect(Button button, Node hoverNode) {
        if (button != null) {
            if (hoverNode != null) {
                hoverNode.setVisible(false);
            }
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    // KHI HOVER VÀO: Đổi ảnh và đổi con trỏ
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (hoverNode != null) {
                        hoverNode.setVisible(true);
                    }
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    // KHI RỜI KHỎI HOVER: Đổi ảnh và đổi con trỏ về mặc định
                    if (hoverNode != null) {
                        hoverNode.setVisible(false);
                    }
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                    }
                }
            });
        }
    }

    // Phương thức chặn phím Space/Enter
    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }

    public void onResumeClick() {
        paused = false;
        pauseScreen.setVisible(false);
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
        SoundManager.getInstance().resumeBackgroundMusic();
    }

    public void onBackClick1() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        paused = false;
        pauseScreen.setVisible(false);
        Main.returnToMenu();
    }

    public void onBackClick2() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        paused = false;
        loseScreen.setVisible(false);
        Main.returnToMenu();
    }

    public void onPLayAgainClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        loseScreen.setVisible(false);
        Main.restartGame();
    }

    /**
     * Xử lý sự kiện khi nhấp vào nút Next Level trên màn hình Win.
     */
    @FXML
    public void onNextLevelClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        System.out.println("OKPPPPPPPPPPPPPPPPPPPK");
        winScreen.setVisible(false);
        paused = false;
        Main.loadNextLevel(); // Gọi phương thức để tải map tiếp theo
    }

    private void initializeLoseScreenVideo() {
        if (lose_mediaPlayer != null) {
            return; // Đã khởi tạo rồi
        }
        try {
            String videoPath = "/Images/background/lose_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            Media lose_backgroundVideo = new Media(videoUrl.toExternalForm());
            lose_mediaPlayer = new MediaPlayer(lose_backgroundVideo);
            lose_backgroundMediaView.setMediaPlayer(lose_mediaPlayer);

            // Thiết lập phát lặp lại và tắt tiếng
            lose_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            lose_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
            // Xử lý lỗi (có thể hiển thị màn hình tĩnh thay thế)
        }
    }

    public void showLoseScreen() {
        initializeLoseScreenVideo();
        loseScreen.setVisible(true);
        paused = true;
        loseScreen.setVisible(true);
        // 3. Bắt đầu chạy video
        if (lose_mediaPlayer != null) {
            lose_mediaPlayer.play();
        }
    }

    public void showWinScreen() {
        initializeWinScreenVideo();
        paused = true;
        winScreen.setVisible(true);
        if (win_mediaPlayer != null) {
            win_mediaPlayer.play();
        }
    }

    public void onBackClick3() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        winScreen.setVisible(false);
        paused = false;
        Main.returnToMenu();
    }

    private void initializeWinScreenVideo() {
        if (win_mediaPlayer != null) {
            return; // Đã khởi tạo rồi
        }

        try {
            String videoPath = "/Images/background/win_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            Media win_backgroundVideo = new Media(videoUrl.toExternalForm());
            win_mediaPlayer = new MediaPlayer(win_backgroundVideo);
            win_backgroundMediaView.setMediaPlayer(win_mediaPlayer);

            win_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            win_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
            // Xử lý lỗi (có thể hiển thị màn hình tĩnh thay thế)
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (pauseScreen != null) {
            pauseScreen.setVisible(false);
        }

        // THÊM: Lắng nghe Scene Property (Áp dụng cho bất kỳ nút nào)
        if (resumeGameButton != null) {
            resumeGameButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        // 2. Áp dụng hiệu ứng Hover mới (Âm thanh + Hiển thị/Ẩn + Con trỏ)
        addHoverEffect(resumeGameButton, resumeHoverImage1);
        addHoverEffect(backButton1, backHoverImage1);

        addHoverEffect(playAgainButton, playAgainHoverImage);
        addHoverEffect(backButton2, backHoverImage2);

        addHoverEffect(backButton3, backHoverImage3);

        // Thêm cho nút Next Level
        addHoverEffect(nextLevelButton, nextLevelHoverImage);

        // 3. Gọi phương thức chặn phím
        preventKeyActivation(resumeGameButton);
        preventKeyActivation(backButton1);
        preventKeyActivation(playAgainButton);
        preventKeyActivation(backButton2);
        preventKeyActivation(backButton3);

        // Chặn phím cho nút Next Level
        preventKeyActivation(nextLevelButton);
    }
}