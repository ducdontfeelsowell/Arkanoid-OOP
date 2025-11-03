package org.example.arkanoid.controller;

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

public class SettingController implements Initializable {
    @FXML
    private Button volumeButton;

    @FXML
    private Button fontButton;

    @FXML
    private Button backButton;

    @FXML
    private ImageView fontHoverImage;

    @FXML
    private ImageView fontImage;

    @FXML
    private ImageView volumeImage;

    @FXML
    private ImageView volumeHoverImage;

    @FXML
    private ImageView backHoverImage;

    @FXML
    private ImageView backImage;

    @FXML Label volumeText;

    @FXML Label fontText;

    @FXML
    public void onVolumeButtonClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        volumeText.setText("tang giam am luong");
    }

    @FXML
    public void onFontButtonClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        fontText.setText("thay doi phong chu");
    }

    @FXML
    public void onBackButton() throws IOException {
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

    public void initialize(URL location, ResourceBundle resources) {
        // back button hover setup
        backHoverImage.setMouseTransparent(true);
        backHoverImage.visibleProperty().bind(backButton.hoverProperty());
        backImage.visibleProperty().bind(backButton.hoverProperty().not());

        // font button hover setup
        fontHoverImage.setMouseTransparent(true);
        fontHoverImage.visibleProperty().bind(fontButton.hoverProperty());
        fontImage.visibleProperty().bind(fontButton.hoverProperty().not());

        // volume button hover setup
        volumeHoverImage.setMouseTransparent(true);
        volumeHoverImage.visibleProperty().bind(volumeButton.hoverProperty());
        volumeImage.visibleProperty().bind(volumeButton.hoverProperty().not());

        addHoverSound(volumeButton);
        addHoverSound(fontButton);
        addHoverSound(backButton);
    }
}