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
    private Media backgroundVideo;

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

    // THÊM MỚI: Các ImageView cho nút độ khó (Level 1)
    @FXML private ImageView level1Easy_out, level1Easy_on;
    @FXML private ImageView level1Normal_out, level1Normal_on;
    @FXML private ImageView level1Hard_out, level1Hard_on;

    // (Lặp lại cho Level 2 đến 12)
    @FXML private ImageView level2Easy_out, level2Easy_on;
    @FXML private ImageView level2Normal_out, level2Normal_on;
    @FXML private ImageView level2Hard_out, level2Hard_on;

    @FXML private ImageView level3Easy_out, level3Easy_on;
    @FXML private ImageView level3Normal_out, level3Normal_on;
    @FXML private ImageView level3Hard_out, level3Hard_on;

    @FXML private ImageView level4Easy_out, level4Easy_on;
    @FXML private ImageView level4Normal_out, level4Normal_on;
    @FXML private ImageView level4Hard_out, level4Hard_on;

    @FXML private ImageView level5Easy_out, level5Easy_on;
    @FXML private ImageView level5Normal_out, level5Normal_on;
    @FXML private ImageView level5Hard_out, level5Hard_on;

    @FXML private ImageView level6Easy_out, level6Easy_on;
    @FXML private ImageView level6Normal_out, level6Normal_on;
    @FXML private ImageView level6Hard_out, level6Hard_on;

    @FXML private ImageView level7Easy_out, level7Easy_on;
    @FXML private ImageView level7Normal_out, level7Normal_on;
    @FXML private ImageView level7Hard_out, level7Hard_on;

    @FXML private ImageView level8Easy_out, level8Easy_on;
    @FXML private ImageView level8Normal_out, level8Normal_on;
    @FXML private ImageView level8Hard_out, level8Hard_on;

    @FXML private ImageView level9Easy_out, level9Easy_on;
    @FXML private ImageView level9Normal_out, level9Normal_on;
    @FXML private ImageView level9Hard_out, level9Hard_on;

    @FXML private ImageView level10Easy_out, level10Easy_on;
    @FXML private ImageView level10Normal_out, level10Normal_on;
    @FXML private ImageView level10Hard_out, level10Hard_on;

    @FXML private ImageView level11Easy_out, level11Easy_on;
    @FXML private ImageView level11Normal_out, level11Normal_on;
    @FXML private ImageView level11Hard_out, level11Hard_on;

    @FXML private ImageView level12Easy_out, level12Easy_on;
    @FXML private ImageView level12Normal_out, level12Normal_on;
    @FXML private ImageView level12Hard_out, level12Hard_on;


    // Các AnchorPane chọn độ khó ---
    @FXML private AnchorPane difficultyPane1, difficultyPane2, difficultyPane3, difficultyPane4,
            difficultyPane5, difficultyPane6, difficultyPane7, difficultyPane8,
            difficultyPane9, difficultyPane10, difficultyPane11, difficultyPane12;

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

    private AnchorPane[] difficultyPanes;
    private Button[] easyButtons, normalButtons, hardButtons;
    // Mảng để quản lý ảnh (cho hiệu ứng khóa)
    private ImageView[] levelOutImages;

    // THÊM MỚI: Mảng cho ảnh nút độ khó
    private ImageView[] easyOutImages, easyOnImages;
    private ImageView[] normalOutImages, normalOnImages;
    private ImageView[] hardOutImages, hardOnImages;


    /**
     * SỬA ĐỔI: Cập nhật khóa cho cả Nút Level và Nút Độ Khó (Dùng ảnh thay vì nút)
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


        // --- 2. SỬA ĐỔI: Cập nhật trạng thái khóa cho Nút Độ Khó (Dùng ảnh) ---
        if (easyButtons == null || easyOutImages == null || easyOnImages == null) return; // (Chưa khởi tạo)

        for (int i = 0; i < 12; i++) {
            if (easyButtons[i] == null || normalButtons[i] == null || hardButtons[i] == null ||
                    easyOutImages[i] == null || normalOutImages[i] == null || hardOutImages[i] == null ||
                    easyOnImages[i] == null || normalOnImages[i] == null || hardOnImages[i] == null) {
                continue;
            }

            int levelIndex = i + 1; // Level 1-12
            int status = difficulties[levelIndex]; // 0, 1, 2, hoặc 3

            // Nút Easy: Luôn mở (nút level cha đã xử lý khóa)
            easyButtons[i].setDisable(false);
            easyOutImages[i].setOpacity(1.0); // CHỈNH SỬA (từ Button sang ImageView)

            // Nút Normal: Khóa nếu Easy chưa xong (status < 1)
            if (status < ProgressManager.STATUS_EASY_COMPLETED) { // status < 1
                normalButtons[i].setDisable(true);
                normalOutImages[i].setOpacity(0.3); // CHỈNH SỬA (từ Button sang ImageView)
                normalOnImages[i].setVisible(false); // THÊM MỚI: Đảm bảo ảnh 'on' tắt
            } else {
                normalButtons[i].setDisable(false);
                normalOutImages[i].setOpacity(1.0); // CHỈNH SỬA (từ Button sang ImageView)
            }

            // Nút Hard: Khóa nếu Normal chưa xong (status < 2)
            if (status < ProgressManager.STATUS_NORMAL_COMPLETED) { // status < 2
                hardButtons[i].setDisable(true);
                hardOutImages[i].setOpacity(0.3); // CHỈNH SỬA (từ Button sang ImageView)
                hardOnImages[i].setVisible(false); // THÊM MỚI: Đảm bảo ảnh 'on' tắt
            } else {
                hardButtons[i].setDisable(false);
                hardOutImages[i].setOpacity(1.0); // CHỈNH SỬA (từ Button sang ImageView)
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

    private void hideAllDifficultyPanes() {
        if (difficultyPanes == null) return;
        for (AnchorPane pane : difficultyPanes) {
            if (pane != null) {
                pane.setVisible(false);
            }
        }
    }

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
     * Xử lý khi nhấp vào nút Easy, Normal, hoặc Hard.
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

    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            if (imageOn != null) {
                imageOn.setVisible(false);
            }
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    // KHI HOVER VÀO:
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) {
                        imageOut.setVisible(false);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(true);
                    }

                    // THÊM MỚI: THAY ĐỔI CON TRỎ THÀNH IMG2 (Hover Cursor)
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }

                } else {
                    // KHI RỜI KHỎI HOVER:
                    if (imageOut != null) {
                        imageOut.setVisible(true);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(false);
                    }
                    System.out.println("PPPPPPPPPP");
                    // THÊM MỚI: ĐẶT LẠI CON TRỎ MẶC ĐỊNH (IMG1) CỦA SCENE
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                        System.out.println("TTTTTTTTTTTTTT");
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

    private void initializeCursors(Scene scene) {
        if (defaultGameCursor == null) {
            try {
                // Tải ảnh img1
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    // Tạo Cursor img1
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    // Áp dụng con trỏ mặc định của game cho Scene
                    scene.setCursor(defaultGameCursor);
                } else {
                    // Fallback nếu không tìm thấy img1
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1): " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }

        // KIỂM TRA THỨ HAI: Tải con trỏ hover (img2)
        if (buttonHoverCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_HOVER_CURSOR);
                if (cursorUrl == null) {
                    buttonHoverCursor = Cursor.HAND;
                    throw new IOException("Không tìm thấy file con trỏ nút. Dùng Cursor.HAND.");
                }

                Image customImage = new Image(cursorUrl.toExternalForm());
                buttonHoverCursor = Cursor.cursor(cursorUrl.toExternalForm());

            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ hover (img2): " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
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
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Lỗi khi tải video nền LevelController: " + e.getMessage());
        }


        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

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

        // THÊM MỚI: Khởi tạo mảng ảnh độ khó
        easyOutImages = new ImageView[]{
                level1Easy_out, level2Easy_out, level3Easy_out, level4Easy_out,
                level5Easy_out, level6Easy_out, level7Easy_out, level8Easy_out,
                level9Easy_out, level10Easy_out, level11Easy_out, level12Easy_out
        };
        easyOnImages = new ImageView[]{
                level1Easy_on, level2Easy_on, level3Easy_on, level4Easy_on,
                level5Easy_on, level6Easy_on, level7Easy_on, level8Easy_on,
                level9Easy_on, level10Easy_on, level11Easy_on, level12Easy_on
        };
        normalOutImages = new ImageView[]{
                level1Normal_out, level2Normal_out, level3Normal_out, level4Normal_out,
                level5Normal_out, level6Normal_out, level7Normal_out, level8Normal_out,
                level9Normal_out, level10Normal_out, level11Normal_out, level12Normal_out
        };
        normalOnImages = new ImageView[]{
                level1Normal_on, level2Normal_on, level3Normal_on, level4Normal_on,
                level5Normal_on, level6Normal_on, level7Normal_on, level8Normal_on,
                level9Normal_on, level10Normal_on, level11Normal_on, level12Normal_on
        };
        hardOutImages = new ImageView[]{
                level1Hard_out, level2Hard_out, level3Hard_out, level4Hard_out,
                level5Hard_out, level6Hard_out, level7Hard_out, level8Hard_out,
                level9Hard_out, level10Hard_out, level11Hard_out, level12Hard_out
        };
        hardOnImages = new ImageView[]{
                level1Hard_on, level2Hard_on, level3Hard_on, level4Hard_on,
                level5Hard_on, level6Hard_on, level7Hard_on, level8Hard_on,
                level9Hard_on, level10Hard_on, level11Hard_on, level12Hard_on
        };


        // (Ẩn tất cả các pane độ khó - FXML đã làm, nhưng để chắc chắn)
        hideAllDifficultyPanes();

        // --- (Code setMouseTransparent cũ) ---
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

        // THÊM MỚI: setMouseTransparent cho ảnh "on" của nút độ khó
        for (ImageView iv : easyOnImages) { if (iv != null) iv.setMouseTransparent(true); }
        for (ImageView iv : normalOnImages) { if (iv != null) iv.setMouseTransparent(true); }
        for (ImageView iv : hardOnImages) { if (iv != null) iv.setMouseTransparent(true); }


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

        // THÊM MỚI: Áp dụng hiệu ứng hover cho nút độ khó
        for(int i=0; i<12; i++) {
            if (easyButtons[i] != null) addHoverEffect(easyButtons[i], easyOutImages[i], easyOnImages[i]);
            if (normalButtons[i] != null) addHoverEffect(normalButtons[i], normalOutImages[i], normalOnImages[i]);
            if (hardButtons[i] != null) addHoverEffect(hardButtons[i], hardOutImages[i], hardOnImages[i]);
        }


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

        // THÊM MỚI: Chặn phím cho nút độ khó
        for (Button b : easyButtons) { preventKeyActivation(b); }
        for (Button b : normalButtons) { preventKeyActivation(b); }
        for (Button b : hardButtons) { preventKeyActivation(b); }

        updateLockStatus();
    }
}