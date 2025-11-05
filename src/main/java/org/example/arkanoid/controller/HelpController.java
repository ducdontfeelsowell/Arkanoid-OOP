package org.example.arkanoid.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode; // THÊM MỚI
import javafx.scene.input.KeyEvent; // THÊM MỚI

public class HelpController implements Initializable {
    private boolean isTransisioning = false;

    @FXML
    private Label helpLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backHoverImage;

    @FXML
    private ImageView backImage;

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
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
        // back button hover setup
        backHoverImage.setMouseTransparent(true);
        backHoverImage.visibleProperty().bind(backButton.hoverProperty());
        backImage.visibleProperty().bind(backButton.hoverProperty().not());

        // font button hover setup
        addHoverSound(backButton);

        // --- THÊM MỚI: Gọi phương thức chặn phím ---
        preventKeyActivation(backButton);
        // --- KẾT THÚC THÊM MỚI ---
    }

}