package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import javafx.scene.image.Image; // THÊM MỚI
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Cursor; // THÊM MỚI
import javafx.scene.Scene; // THÊM MỚI
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SettingController implements Initializable {
    private static Cursor defaultGameCursor; // Con trỏ mặc định của game (img1)
    private static Cursor buttonHoverCursor; // Con trỏ khi hover (img2)

    @FXML
    private Button backButton;
    @FXML
    private ImageView backHoverImage; // Ảnh khi hover
    @FXML
    private ImageView backImage; // Ảnh mặc định (out)

    @FXML private Slider volumeSlider;
    @FXML private CheckBox muteCheckbox;

    // --- KHAI BÁO FXML CHO ĐỘ KHÓ ---
    @FXML private RadioButton easyRadio;
    @FXML private RadioButton normalRadio;
    @FXML private RadioButton hardRadio;
    @FXML private ToggleGroup difficultyToggleGroup;
    // --- KẾT THÚC KHAI BÁO ---

    @FXML private ImageView volumeIconViewHigh;
    @FXML private ImageView volumeIconViewMuted;

    @FXML
    private MediaView settingPlayerView;

    private SoundManager soundManager;

    @FXML
    public void onBackButton() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

    // --- CÁC PHƯƠNG THỨC XỬ LÝ ĐỘ KHÓ (Giữ nguyên) ---
    @FXML
    private void onEasyClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateDifficultySettings("Dễ");
    }

    @FXML
    private void onNormalClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateDifficultySettings("Thường");
    }

    @FXML
    private void onHardClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateDifficultySettings("Khó");
    }

    /**
     * Cập nhật các biến CURRENT trong Constants (Giữ nguyên)
     */
    private void updateDifficultySettings(String difficulty) {
        Constants.CURRENT_DIFFICULTY = difficulty;

        switch (difficulty) {
            case "Dễ":
                Constants.CURRENT_LIVES = Constants.EASY_LIVES;
                Constants.CURRENT_PADDLE_SPEED = Constants.EASY_PADDLE_SPEED;
                Constants.CURRENT_BALL_SPEED = Constants.EASY_BALL_SPEED;
                Constants.CURRENT_DROP_CHANCE = Constants.EASY_DROP_CHANCE;
                break;
            case "Thường":
                Constants.CURRENT_LIVES = Constants.NORMAL_LIVES;
                Constants.CURRENT_PADDLE_SPEED = Constants.NORMAL_PADDLE_SPEED;
                Constants.CURRENT_BALL_SPEED = Constants.NORMAL_BALL_SPEED;
                Constants.CURRENT_DROP_CHANCE = Constants.NORMAL_DROP_CHANCE;
                break;
            case "Khó":
                Constants.CURRENT_LIVES = Constants.HARD_LIVES;
                Constants.CURRENT_PADDLE_SPEED = Constants.HARD_PADDLE_SPEED;
                Constants.CURRENT_BALL_SPEED = Constants.HARD_BALL_SPEED;
                Constants.CURRENT_DROP_CHANCE = Constants.HARD_DROP_CHANCE;
                break;
        }
    }
    // --- KẾT THÚC PHƯƠNG THỨC ĐỘ KHÓ ---


    // PHƯƠNG THỨC ĐÃ SỬA: Thêm logic đổi con trỏ
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            // Đảm bảo ảnh ON/HOVER ban đầu bị ẩn
            if (imageOn != null) {
                imageOn.setVisible(false);
            }

            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    // KHI HOVER VÀO: Đổi ảnh và đổi con trỏ
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) {
                        imageOut.setVisible(false);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(true);
                    }
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    // KHI RỜI KHỎI HOVER: Đổi ảnh và đổi con trỏ về mặc định
                    if (imageOut != null) {
                        imageOut.setVisible(true);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(false);
                    }
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                    }
                }
            });
        }
    }


    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }
    // ... (Phương thức preventKeyActivation(RadioButton) giữ nguyên) ...

    private void initializeCursors(Scene scene) {
        // Khởi tạo con trỏ mặc định (img1)
        if (defaultGameCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    // Áp dụng con trỏ mặc định cho Scene
                    scene.setCursor(defaultGameCursor);
                } else {
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong SettingController: " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }

        // Khởi tạo con trỏ hover (img2)
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
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong SettingController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundManager = SoundManager.getInstance();

        try {
            String resourcePath = "/Images/background/video_main_menu.mp4";

            URL videoResource = getClass().getResource(resourcePath);
            Media media = new Media(videoResource.toExternalForm());
            MediaPlayer setting_mediaPlayer = new MediaPlayer(media);
            settingPlayerView.setMediaPlayer(setting_mediaPlayer);
            setting_mediaPlayer.setAutoPlay(true);
            setting_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            setting_mediaPlayer.setMute(true); // Tắt tiếng video nền
            setting_mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải hoặc phát video.");
        }

        // THÊM MỚI: Lắng nghe Scene Property
        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        backHoverImage.setMouseTransparent(true);

        addHoverEffect(backButton, backImage, backHoverImage);
        preventKeyActivation(backButton);

        // ... (Phần còn lại giữ nguyên) ...
    }
}