package org.example.arkanoid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane; // THÊM MỚI
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
    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    // Các nút Level (Map)
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

    // --- Các ImageView ON/OUT (Của Nút Level) ---
    @FXML private ImageView Level1_on, Level2_on, Level3_on, Level4_on, Level5_on, Level6_on,
            Level7_on, Level8_on, Level9_on, Level10_on, Level11_on, Level12_on;
    @FXML private ImageView Level1_out, Level2_out, Level3_out, Level4_out, Level5_out, Level6_out,
            Level7_out, Level8_out, Level9_out, Level10_out, Level11_out, Level12_out;
    @FXML private ImageView back_button_out, back_button_on;
    // ----------------------------

    // --- THÊM MỚI: Các AnchorPane chọn độ khó ---
    @FXML private AnchorPane difficultyPane1, difficultyPane2, difficultyPane3, difficultyPane4,
            difficultyPane5, difficultyPane6, difficultyPane7, difficultyPane8,
            difficultyPane9, difficultyPane10, difficultyPane11, difficultyPane12;

    // --- THÊM MỚI: Các nút chọn độ khó ---
    @FXML private Button level1EasyButton, level1NormalButton, level1HardButton;
    @FXML private Button level2EasyButton, level2NormalButton, level2HardButton;
    @FXML private Button level3EasyButton, level3NormalButton, level3HardButton;
    @FXML private Button level4EasyButton, level4NormalButton, level4HardButton;
    @FXML private Button level5EasyButton, level5NormalButton, level5HardButton;
    @FXML private Button level6EasyButton, level6NormalButton, level6HardButton;
    @FXML private Button level7EasyButton, level7NormalButton, level7HardButton;
    @FXML private Button level8EasyButton, level8NormalButton, level8HardButton;
    @FXML private Button level9EasyButton, level9NormalButton, level9HardButton;
    @FXML private Button level10EasyButton, level10NormalButton, level10HardButton;
    @FXML private Button level11EasyButton, level11NormalButton, level11HardButton;
    @FXML private Button level12EasyButton, level12NormalButton, level12HardButton;

    // --- THÊM MỚI: Mảng để quản lý các control ---
    private AnchorPane[] difficultyPanes;
    private Button[] easyButtons, normalButtons, hardButtons;
    // Mảng để quản lý ảnh (cho hiệu ứng khóa)
    private ImageView[] levelOutImages;


    /**
     * SỬA ĐỔI: Cập nhật khóa cho cả Nút Level và Nút Độ Khó
     */
    public void updateLockStatus() {
        int unlockedLevel = ProgressManager.maxLevelUnlocked;
        int[] difficulties = ProgressManager.currentDifficultyCompleted; // Mảng trạng thái

        // --- 1. Khóa Nút Level (Logic cũ) ---
        // (Bật lại tất cả trước khi khóa)
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
        if (unlockedLevel < 2) { map2Button.setDisable(true); Level2_out.setOpacity(0.3); }
        if (unlockedLevel < 3) { map3Button.setDisable(true); Level3_out.setOpacity(0.3); }
        if (unlockedLevel < 4) { map4Button.setDisable(true); Level4_out.setOpacity(0.3); }
        if (unlockedLevel < 5) { map5Button.setDisable(true); Level5_out.setOpacity(0.3); }
        if (unlockedLevel < 6) { map6Button.setDisable(true); Level6_out.setOpacity(0.3); }
        if (unlockedLevel < 7) { map7Button.setDisable(true); Level7_out.setOpacity(0.3); }
        if (unlockedLevel < 8) { map8Button.setDisable(true); Level8_out.setOpacity(0.3); }
        if (unlockedLevel < 9) { map9Button.setDisable(true); Level9_out.setOpacity(0.3); }
        if (unlockedLevel < 10) { map10Button.setDisable(true); Level10_out.setOpacity(0.3); }
        if (unlockedLevel < 11) { map11Button.setDisable(true); Level11_out.setOpacity(0.3); }
        if (unlockedLevel < 12) { map12Button.setDisable(true); Level12_out.setOpacity(0.3); }


        // --- 2. THÊM MỚI: Cập nhật trạng thái khóa cho Nút Độ Khó ---
        if (easyButtons == null) return; // (Chưa khởi tạo)

        for (int i = 0; i < 12; i++) {
            if (easyButtons[i] == null || normalButtons[i] == null || hardButtons[i] == null) {
                continue;
            }

            int levelIndex = i + 1; // Level 1-12
            int status = difficulties[levelIndex]; // 0, 1, 2, hoặc 3

            // Nút Easy: Luôn mở (nút level cha đã xử lý khóa)
            easyButtons[i].setDisable(false);
            easyButtons[i].setOpacity(1.0);

            // Nút Normal: Khóa nếu Easy chưa xong (status < 1)
            if (status < ProgressManager.STATUS_EASY_COMPLETED) { // status < 1
                normalButtons[i].setDisable(true);
                normalButtons[i].setOpacity(0.3); // (Thêm hiệu ứng mờ)
            } else {
                normalButtons[i].setDisable(false);
                normalButtons[i].setOpacity(1.0);
            }

            // Nút Hard: Khóa nếu Normal chưa xong (status < 2)
            if (status < ProgressManager.STATUS_NORMAL_COMPLETED) { // status < 2
                hardButtons[i].setDisable(true);
                hardButtons[i].setOpacity(0.3);
            } else {
                hardButtons[i].setDisable(false);
                hardButtons[i].setOpacity(1.0);
            }
        }
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

    // --- THÊM MỚI: Ẩn tất cả các pane chọn độ khó ---
    private void hideAllDifficultyPanes() {
        if (difficultyPanes == null) return;
        for (AnchorPane pane : difficultyPanes) {
            if (pane != null) {
                pane.setVisible(false);
            }
        }
    }

    // --- THÊM MỚI: Hiển thị (hoặc ẩn) một pane độ khó cụ thể ---
    private void showDifficultyPane(int levelIndex) {
        if (difficultyPanes == null || levelIndex < 1 || levelIndex > 12) return;

        AnchorPane paneToShow = difficultyPanes[levelIndex - 1];
        if (paneToShow == null) return;

        if (paneToShow.isVisible()) {
            // Nếu đã hiển thị, ẩn đi
            paneToShow.setVisible(false);
        } else {
            // Ẩn tất cả các pane khác
            hideAllDifficultyPanes();
            // Cập nhật lại trạng thái khóa (vì dữ liệu có thể đã thay đổi)
            updateLockStatus();
            // Hiển thị pane này
            paneToShow.setVisible(true);
        }
    }


    // --- SỬA ĐỔI: Các hàm onClickMap (1-12) ---
    // (Thay vì startGame, chúng ta gọi showDifficultyPane)

    public void onClickMap1() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        showDifficultyPane(1);
    }

    public void onClickMap2() {
        if (ProgressManager.maxLevelUnlocked >= 2) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(2);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap3() {
        if (ProgressManager.maxLevelUnlocked >= 3) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(3);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap4() {
        if (ProgressManager.maxLevelUnlocked >= 4) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(4);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap5() {
        if (ProgressManager.maxLevelUnlocked >= 5) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(5);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap6() {
        if (ProgressManager.maxLevelUnlocked >= 6) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(6);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap7() {
        if (ProgressManager.maxLevelUnlocked >= 7) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(7);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap8() {
        if (ProgressManager.maxLevelUnlocked >= 8) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(8);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap9() {
        if (ProgressManager.maxLevelUnlocked >= 9) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(9);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap10() {
        if (ProgressManager.maxLevelUnlocked >= 10) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(10);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap11() {
        if (ProgressManager.maxLevelUnlocked >= 11) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(11);
        } else {
            showLockedLevelMessage();
        }
    }

    public void onClickMap12() {
        if (ProgressManager.maxLevelUnlocked >= 12) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            showDifficultyPane(12);
        } else {
            showLockedLevelMessage();
        }
    }

    /**
     * THÊM MỚI: Xử lý khi nhấp vào nút Easy, Normal, hoặc Hard.
     */
    @FXML
    public void onDifficultyClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String fxId = clickedButton.getId(); // Ví dụ: "level1EasyButton"

        if (fxId == null) return;

        int level = 0;
        int difficulty = 0;

        // Phân tích fx:id để tìm Level (1-12)
        if (fxId.startsWith("level1Easy") || fxId.startsWith("level1Normal") || fxId.startsWith("level1Hard")) level = 1;
        else if (fxId.startsWith("level2")) level = 2;
        else if (fxId.startsWith("level3")) level = 3;
        else if (fxId.startsWith("level4")) level = 4;
        else if (fxId.startsWith("level5")) level = 5;
        else if (fxId.startsWith("level6")) level = 6;
        else if (fxId.startsWith("level7")) level = 7;
        else if (fxId.startsWith("level8")) level = 8;
        else if (fxId.startsWith("level9")) level = 9;
        else if (fxId.startsWith("level10")) level = 10;
        else if (fxId.startsWith("level11")) level = 11;
        else if (fxId.startsWith("level12")) level = 12;

        // Phân tích fx:id để tìm Difficulty (0, 1, 2)
        if (fxId.contains("Easy")) difficulty = ProgressManager.DIFFICULTY_EASY;
        else if (fxId.contains("Normal")) difficulty = ProgressManager.DIFFICULTY_NORMAL;
        else if (fxId.contains("Hard")) difficulty = ProgressManager.DIFFICULTY_HARD;


        if (level > 0) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);

            // 1. Set độ khó toàn cục trong Constants
            Constants.setDifficulty(difficulty);

            // 2. Ẩn tất cả các pane (vì chúng ta sắp rời đi)
            hideAllDifficultyPanes();

            // 3. Bắt đầu game
            Main.startGame(level);
        }
    }


    public void onBackButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

    // ... (Giữ nguyên initializeCursors, addHoverEffect, preventKeyActivation) ...
    private void initializeCursors(Scene scene) {
        // ...
    }
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        // ...
    }
    private void preventKeyActivation(Button button) {
        // ...
    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DEBUG: LevelController Initialized.");
        // ... (Code load video cũ) ...
        try {
            String videoPath = "/Images/background/level_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }
            Media backgroundVideo = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(backgroundVideo);
            backgroundMediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi tải video nền LevelController: " + e.getMessage());
        }

        // Lắng nghe Scene Property
        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        // --- THÊM MỚI: Nhóm các control vào mảng ---
        difficultyPanes = new AnchorPane[]{
                difficultyPane1, difficultyPane2, difficultyPane3, difficultyPane4,
                difficultyPane5, difficultyPane6, difficultyPane7, difficultyPane8,
                difficultyPane9, difficultyPane10, difficultyPane11, difficultyPane12
        };

        easyButtons = new Button[]{
                level1EasyButton, level2EasyButton, level3EasyButton, level4EasyButton,
                level5EasyButton, level6EasyButton, level7EasyButton, level8EasyButton,
                level9EasyButton, level10EasyButton, level11EasyButton, level12EasyButton
        };
        normalButtons = new Button[]{
                level1NormalButton, level2NormalButton, level3NormalButton, level4NormalButton,
                level5NormalButton, level6NormalButton, level7NormalButton, level8NormalButton,
                level9NormalButton, level10NormalButton, level11NormalButton, level12NormalButton
        };
        hardButtons = new Button[]{
                level1HardButton, level2HardButton, level3HardButton, level4HardButton,
                level5HardButton, level6HardButton, level7HardButton, level8HardButton,
                level9HardButton, level10HardButton, level11HardButton, level12HardButton
        };

        levelOutImages = new ImageView[]{
                Level1_out, Level2_out, Level3_out, Level4_out, Level5_out, Level6_out,
                Level7_out, Level8_out, Level9_out, Level10_out, Level11_out, Level12_out
        };

        // (Ẩn tất cả các pane độ khó - FXML đã làm, nhưng để chắc chắn)
        hideAllDifficultyPanes();

        // --- (Code setMouseTransparent cũ) ---
        Level1_on.setMouseTransparent(true);
        Level2_on.setMouseTransparent(true);
        // ... (lặp lại cho đến 12) ...
        Level12_on.setMouseTransparent(true);
        back_button_on.setMouseTransparent(true);

        // --- (Code addHoverEffect cũ cho các nút Level) ---
        addHoverEffect(map1Button, Level1_out, Level1_on);
        addHoverEffect(map2Button, Level2_out, Level2_on);
        // ... (lặp lại cho đến 12) ...
        addHoverEffect(map12Button, Level12_out, Level12_on);
        addHoverEffect(backButton, back_button_out, back_button_on);

        // --- (Code preventKeyActivation cũ cho các nút Level) ---
        preventKeyActivation(map1Button);
        preventKeyActivation(map2Button);
        // ... (lặp lại cho đến 12) ...
        preventKeyActivation(map12Button);
        preventKeyActivation(backButton);

        // (Không cần addHoverEffect hoặc preventKeyActivation cho các nút Easy/Normal/Hard
        // vì chúng là các nút ẩn, trong suốt, nằm dưới các ImageView đã có hiệu ứng)

        // Cập nhật trạng thái khóa
        updateLockStatus();
    }
}