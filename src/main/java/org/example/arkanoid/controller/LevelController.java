package org.example.arkanoid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Node;
import javafx.util.Duration;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.game.ProgressManager;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LevelController implements Initializable{
    private static Cursor defaultGameCursor; // Con trỏ mặc định của game (img1)
    private static Cursor buttonHoverCursor; // Con trỏ khi hover (img2)

    public Button map1Button;
    public Button map2Button;
    public Button map3Button;
    public Button map4Button;
    public Button map5Button;
    public Button map6Button;
    public Button map7Button;
    public Button map8Button;
    public Button map9Button;
    public Button map10Button;
    public Button map11Button;
    public Button map12Button;
    public Button backButton;


    @FXML
    private MediaView backgroundMediaView;

    private MediaPlayer mediaPlayer;
    private Media backgroundVideo;

    // --- Các ImageView ON/OUT ---
    @FXML private ImageView Level1_on;
    @FXML private ImageView Level2_on;
    @FXML private ImageView Level3_on;
    @FXML private ImageView Level4_on;
    @FXML private ImageView Level5_on;
    @FXML private ImageView Level6_on;
    @FXML private ImageView Level7_on;
    @FXML private ImageView Level8_on;
    @FXML private ImageView Level9_on;
    @FXML private ImageView Level10_on;
    @FXML private ImageView Level11_on;
    @FXML private ImageView Level12_on;

    @FXML private ImageView Level1_out;
    @FXML private ImageView Level2_out;
    @FXML private ImageView Level3_out;
    @FXML private ImageView Level4_out;
    @FXML private ImageView Level5_out;
    @FXML private ImageView Level6_out;
    @FXML private ImageView Level7_out;
    @FXML private ImageView Level8_out;
    @FXML private ImageView Level9_out;
    @FXML private ImageView Level10_out;
    @FXML private ImageView Level11_out;
    @FXML private ImageView Level12_out;

    @FXML private ImageView back_button_out;
    @FXML private ImageView back_button_on;
    // ----------------------------

    public void updateLockStatus() {
        int unlocked = ProgressManager.maxLevelUnlocked;

        // Bỏ vô hiệu hóa (setDisable(false)) trước khi kiểm tra
        // để đảm bảo các màn đã mở được bật lại
        map2Button.setDisable(false); Level2_out.setOpacity(1.0);
        map3Button.setDisable(false); Level3_out.setOpacity(1.0);
        map4Button.setDisable(false); Level4_out.setOpacity(1.0);
        map5Button.setDisable(false); Level5_out.setOpacity(1.0);
        map6Button.setDisable(false); Level6_out.setOpacity(1.0);
        map7Button.setDisable(false); Level7_out.setOpacity(1.0);
        map8Button.setDisable(false); Level8_out.setOpacity(1.0);
        map9Button.setDisable(false); Level9_out.setOpacity(1.0);
        map10Button.setDisable(false); Level10_out.setOpacity(1.0);
        map11Button.setDisable(false); Level11_out.setOpacity(1.0);
        map12Button.setDisable(false); Level12_out.setOpacity(1.0);

        // Làm mờ và khóa các màn bị khóa
        if (unlocked < 2) { map2Button.setDisable(true); Level2_out.setOpacity(0.3); }
        if (unlocked < 3) { map3Button.setDisable(true); Level3_out.setOpacity(0.3); }
        if (unlocked < 4) { map4Button.setDisable(true); Level4_out.setOpacity(0.3); }
        if (unlocked < 5) { map5Button.setDisable(true); Level5_out.setOpacity(0.3); }
        if (unlocked < 6) { map6Button.setDisable(true); Level6_out.setOpacity(0.3); }
        if (unlocked < 7) { map7Button.setDisable(true); Level7_out.setOpacity(0.3); }
        if (unlocked < 8) { map8Button.setDisable(true); Level8_out.setOpacity(0.3); }
        if (unlocked < 9) { map9Button.setDisable(true); Level9_out.setOpacity(0.3); }
        if (unlocked < 10) { map10Button.setDisable(true); Level10_out.setOpacity(0.3); }
        if (unlocked < 11) { map11Button.setDisable(true); Level11_out.setOpacity(0.3); }
        if (unlocked < 12) { map12Button.setDisable(true); Level12_out.setOpacity(0.3); }
    }

    private void showLockedLevelMessage() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cấp độ bị khóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn phải hoàn thành các cấp độ trước để mở khóa màn chơi này!");


        Stage stage = (Stage) backButton.getScene().getWindow();
        alert.initOwner(stage);

        alert.showAndWait();
    }

    public void onClickMap1() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        Main.startGame(1);
    }

    public void onClickMap2() {
        if (ProgressManager.maxLevelUnlocked >= 2) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(2);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap3() {
        if (ProgressManager.maxLevelUnlocked >= 3) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(3);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap4() {
        if (ProgressManager.maxLevelUnlocked >= 4) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(4);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap5() {
        if (ProgressManager.maxLevelUnlocked >= 5) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(5);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap6() {
        if (ProgressManager.maxLevelUnlocked >= 6) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(6);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap7() {
        if (ProgressManager.maxLevelUnlocked >= 7) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(7);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap8() {
        if (ProgressManager.maxLevelUnlocked >= 8) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(8);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap9() {
        if (ProgressManager.maxLevelUnlocked >= 9) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(9);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap10() {
        if (ProgressManager.maxLevelUnlocked >= 10) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(10);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap11() {
        if (ProgressManager.maxLevelUnlocked >= 11) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(11);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap12() {
        if (ProgressManager.maxLevelUnlocked >= 12) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(12);
        } else {
            showLockedLevelMessage();
        }
    }


    public void onBackButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

    // PHƯƠNG THỨC KHỞI TẠO CURSOR
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
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong LevelController: " + e.getMessage());
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
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong LevelController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }




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

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DEBUG: LevelController Initialized.");
        try {
            String videoPath = "/Images/background/level_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }
            Media backgroundVideo = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(backgroundVideo);
            backgroundMediaView.setMediaPlayer(mediaPlayer);
            // Thiết lập phát lặp lại
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            // 3. Tạo MediaView
            mediaPlayer.play();
        } catch (NullPointerException e) {
            System.err.println("Lỗi: Không tìm thấy file video. Vui lòng kiểm tra đường dẫn.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        // Tối ưu hóa: Loại bỏ các ràng buộc visibleProperty().bind() thủ công
        // và sử dụng phương thức addHoverEffect mới.

        Level1_on.setMouseTransparent(true);
        Level2_on.setMouseTransparent(true);
        Level3_on.setMouseTransparent(true);
        Level4_on.setMouseTransparent(true);
        Level5_on.setMouseTransparent(true);
        Level6_on.setMouseTransparent(true);
        Level7_on.setMouseTransparent(true);
        Level8_on.setMouseTransparent(true);
        Level9_on.setMouseTransparent(true);
        Level10_on.setMouseTransparent(true);
        Level11_on.setMouseTransparent(true);
        Level12_on.setMouseTransparent(true);
        back_button_on.setMouseTransparent(true);

        // ÁP DỤNG HIỆU ỨNG HOVER MỚI (Âm thanh + Ẩn/Hiện + Con trỏ)
        addHoverEffect(map1Button, Level1_out, Level1_on);
        addHoverEffect(map2Button, Level2_out, Level2_on);
        addHoverEffect(map3Button, Level3_out, Level3_on);
        addHoverEffect(map4Button, Level4_out, Level4_on);
        addHoverEffect(map5Button, Level5_out, Level5_on);
        addHoverEffect(map6Button, Level6_out, Level6_on);
        addHoverEffect(map7Button, Level7_out, Level7_on);
        addHoverEffect(map8Button, Level8_out, Level8_on);
        addHoverEffect(map9Button, Level9_out, Level9_on);
        addHoverEffect(map10Button, Level10_out, Level10_on);
        addHoverEffect(map11Button, Level11_out, Level11_on);
        addHoverEffect(map12Button, Level12_out, Level12_on);
        addHoverEffect(backButton, back_button_out, back_button_on);


        // Gọi phương thức chặn phím
        preventKeyActivation(map1Button);
        preventKeyActivation(map2Button);
        preventKeyActivation(map3Button);
        preventKeyActivation(map4Button);
        preventKeyActivation(map5Button);
        preventKeyActivation(map6Button);
        preventKeyActivation(map7Button);
        preventKeyActivation(map8Button);
        preventKeyActivation(map9Button);
        preventKeyActivation(map10Button);
        preventKeyActivation(map11Button);
        preventKeyActivation(map12Button);
        preventKeyActivation(backButton);

        updateLockStatus();
    }
}