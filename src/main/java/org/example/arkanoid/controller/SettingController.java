package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton; // THÊM MỚI
import javafx.scene.control.ToggleGroup; // THÊM MỚI
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SettingController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private ImageView backHoverImage;
    @FXML
    private ImageView backImage;

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

    private SoundManager soundManager;

    @FXML
    public void onBackButton() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();

        backButton.getScene().setRoot(root);
    }

    // --- CÁC PHƯƠNG THỨC XỬ LÝ ĐỘ KHÓ ---
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
     * Cập nhật các biến CURRENT trong Constants
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

    private void addHoverSound(Button button) {
        if (button != null) {
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
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

    // Overload cho RadioButton (chỉ chặn Enter)
    private void preventKeyActivation(RadioButton button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }

    private void updateVolumeUI() {
        if (soundManager == null) return;

        boolean isMuted = soundManager.isMuted();
        double currentVolume = soundManager.getMusicVolume();

        muteCheckbox.setSelected(!isMuted);
        volumeSlider.setDisable(isMuted);

        if (isMuted) {
            if (volumeIconViewMuted != null) volumeIconViewMuted.setVisible(true);
            if (volumeIconViewHigh != null) volumeIconViewHigh.setVisible(false);
        } else {
            if (volumeIconViewMuted != null) volumeIconViewMuted.setVisible(false);
            if (volumeIconViewHigh != null) volumeIconViewHigh.setVisible(true);
            volumeSlider.setValue(currentVolume);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundManager = SoundManager.getInstance();

        // Cài đặt Âm lượng
        if (volumeSlider != null) {
            volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                double newVolume = newVal.doubleValue();
                soundManager.setMusicVolume(newVolume);

                if (newVolume == 0 && !soundManager.isMuted()) {
                    soundManager.toggleMute();
                    updateVolumeUI();
                }
                else if (newVolume > 0 && soundManager.isMuted()) {
                    soundManager.toggleMute();
                    updateVolumeUI();
                }
            });
        }
        if (muteCheckbox != null) {
            muteCheckbox.setOnAction(event -> {
                soundManager.toggleMute();
                updateVolumeUI();
            });
        }
        if (volumeIconViewHigh != null) volumeIconViewHigh.setMouseTransparent(true);
        if (volumeIconViewMuted != null) volumeIconViewMuted.setMouseTransparent(true);
        updateVolumeUI();

        // --- SỬA ĐỔI: Cài đặt Độ khó ---
        // Cập nhật giá trị trong Constants (để phòng trường hợp game khởi động lại)
        updateDifficultySettings(Constants.CURRENT_DIFFICULTY);

        // Chọn RadioButton tương ứng
        if (difficultyToggleGroup != null) {
            switch (Constants.CURRENT_DIFFICULTY) {
                case "Dễ":
                    easyRadio.setSelected(true);
                    break;
                case "Thường":
                    normalRadio.setSelected(true);
                    break;
                case "Khó":
                    hardRadio.setSelected(true);
                    break;
            }
        }
        // --- KẾT THÚC SỬA ĐỔI ---

        // Cài đặt Nút Back
        backHoverImage.setMouseTransparent(true);
        backHoverImage.visibleProperty().bind(backButton.hoverProperty());
        backImage.visibleProperty().bind(backButton.hoverProperty().not());
        addHoverSound(backButton);
        preventKeyActivation(backButton);

        // Chặn phím cho RadioButtons
        preventKeyActivation(easyRadio);
        preventKeyActivation(normalRadio);
        preventKeyActivation(hardRadio);
    }
}