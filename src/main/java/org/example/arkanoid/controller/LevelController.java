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
import javafx.util.Duration;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.game.ProgressManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode; // THÊM MỚI
import javafx.scene.input.KeyEvent; // THÊM MỚI
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LevelController implements Initializable{
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

    @FXML
    private MediaView backgroundMediaView; // Đã thêm

    private MediaPlayer mediaPlayer; // Đã thêm
    private Media backgroundVideo; // Đã thêm

    @FXML
    private ImageView Level1_on;

    @FXML
    private ImageView Level2_on;

    @FXML
    private ImageView Level3_on;

    @FXML
    private ImageView Level4_on;

    @FXML
    private ImageView Level5_on;

    @FXML
    private ImageView Level6_on;

    @FXML
    private ImageView Level7_on;

    @FXML
    private ImageView Level8_on;

    @FXML
    private ImageView Level9_on;

    @FXML
    private ImageView Level10_on;

    @FXML
    private ImageView Level11_on;

    @FXML
    private ImageView Level12_on;

    @FXML
    private ImageView Level1_out;

    @FXML
    private ImageView Level2_out;

    @FXML
    private ImageView Level3_out;

    @FXML
    private ImageView Level4_out;

    @FXML
    private ImageView Level5_out;

    @FXML
    private ImageView Level6_out;

    @FXML
    private ImageView Level7_out;

    @FXML
    private ImageView Level8_out;

    @FXML
    private ImageView Level9_out;

    @FXML
    private ImageView Level10_out;

    @FXML
    private ImageView Level11_out;

    @FXML
    private ImageView Level12_out;

    @FXML
    private ImageView back_button_out;

    @FXML
    private ImageView back_button_on;

    public Button backButton;

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
        if (ProgressManager.maxLevelUnlocked >= 2) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(2);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap3() {
        if (ProgressManager.maxLevelUnlocked >= 3) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(3);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap4() {
        if (ProgressManager.maxLevelUnlocked >= 4) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(4);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap5() {
        if (ProgressManager.maxLevelUnlocked >= 5) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(5);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap6() {
        if (ProgressManager.maxLevelUnlocked >= 6) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(6);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap7() {
        if (ProgressManager.maxLevelUnlocked >= 7) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(7);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap8() {
        if (ProgressManager.maxLevelUnlocked >= 8) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(8);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap9() {
        if (ProgressManager.maxLevelUnlocked >= 9) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(9);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap10() {
        if (ProgressManager.maxLevelUnlocked >= 10) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(10);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap11() {
        if (ProgressManager.maxLevelUnlocked >= 11) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(11);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }

    public void onClickMap12() {
        if (ProgressManager.maxLevelUnlocked >= 12) { // <-- KIỂM TRA
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
            Main.startGame(12);
        } else {
            showLockedLevelMessage(); // <-- HIỆN THÔNG BÁO
        }
    }


    public void onBackButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setRoot(root);
    }

    private void addHoverSound(Button button) {
        if (button != null) {
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                }
            });
        }
    }

    // --- THÊM MỚI: Phương thức chặn phím Space/Enter ---
    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }
    // --- KẾT THÚC THÊM MỚI ---

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DEBUG: SettingController Initialized.");
        try {
            String videoPath = "/Images/background/video_level.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                // Ném lỗi rõ ràng nếu không tìm thấy, không dựa vào Objects.requireNonNull
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }
            backgroundVideo = new Media(videoUrl.toExternalForm());
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

        Level1_on.setMouseTransparent(true);
        Level1_on.visibleProperty().bind(map1Button.hoverProperty());
        Level1_out.visibleProperty().bind(map1Button.hoverProperty().not());

        // Setting button hover setup
        Level2_on.setMouseTransparent(true);
        Level2_on.visibleProperty().bind(map2Button.hoverProperty());
        Level2_out.visibleProperty().bind(map2Button.hoverProperty().not());

        // Help button hover setup
        Level3_on.setMouseTransparent(true);
        Level3_on.visibleProperty().bind(map3Button.hoverProperty());
        Level3_out.visibleProperty().bind(map3Button.hoverProperty().not());
        // Shop button hover setup
        Level4_on.setMouseTransparent(true);
        Level4_on.visibleProperty().bind(map4Button.hoverProperty());
        Level4_out.visibleProperty().bind(map4Button.hoverProperty().not());

        Level5_on.setMouseTransparent(true);
        Level5_on.visibleProperty().bind(map5Button.hoverProperty());
        Level5_out.visibleProperty().bind(map5Button.hoverProperty().not());

        Level6_on.setMouseTransparent(true);
        Level6_on.visibleProperty().bind(map6Button.hoverProperty());
        Level6_out.visibleProperty().bind(map6Button.hoverProperty().not());

        Level7_on.setMouseTransparent(true);
        Level7_on.visibleProperty().bind(map7Button.hoverProperty());
        Level7_out.visibleProperty().bind(map7Button.hoverProperty().not());

        // Setting button hover setup
        Level8_on.setMouseTransparent(true);
        Level8_on.visibleProperty().bind(map8Button.hoverProperty());
        Level8_out.visibleProperty().bind(map8Button.hoverProperty().not());

        // Help button hover setup
        Level9_on.setMouseTransparent(true);
        Level9_on.visibleProperty().bind(map9Button.hoverProperty());
        Level9_out.visibleProperty().bind(map9Button.hoverProperty().not());
        // Shop button hover setup
        Level10_on.setMouseTransparent(true);
        Level10_on.visibleProperty().bind(map10Button.hoverProperty());
        Level10_out.visibleProperty().bind(map10Button.hoverProperty().not());

        Level11_on.setMouseTransparent(true);
        Level11_on.visibleProperty().bind(map11Button.hoverProperty());
        Level11_out.visibleProperty().bind(map11Button.hoverProperty().not());

        Level12_on.setMouseTransparent(true);
        Level12_on.visibleProperty().bind(map12Button.hoverProperty());
        Level12_out.visibleProperty().bind(map12Button.hoverProperty().not());

        back_button_on.setMouseTransparent(true);
        back_button_on.visibleProperty().bind(backButton.hoverProperty());
        back_button_out.visibleProperty().bind(backButton.hoverProperty().not());

        addHoverSound(map1Button);
        addHoverSound(map2Button);
        addHoverSound(map3Button);
        addHoverSound(map4Button);
        addHoverSound(map5Button);
        addHoverSound(map6Button);
        addHoverSound(map7Button);
        addHoverSound(map8Button);
        addHoverSound(map9Button);
        addHoverSound(map10Button);
        addHoverSound(map11Button);
        addHoverSound(map12Button);
        addHoverSound(backButton);

        // --- THÊM MỚI: Gọi phương thức chặn phím ---
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
        // --- KẾT THÚC THÊM MỚI ---
        updateLockStatus();
    }
}