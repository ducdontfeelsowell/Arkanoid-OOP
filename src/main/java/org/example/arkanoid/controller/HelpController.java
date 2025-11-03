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

public class HelpController implements Initializable {
    private boolean isTransisioning = false;
    @FXML
    private Button helpButton;

    @FXML
    private Label helpLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView helpImage;

    @FXML
    private ImageView helpHoverImage;

    @FXML
    private ImageView backHoverImage;

    @FXML
    private ImageView backImage;

    @FXML
    private void onHelpButton() {
        helpLabel.setText("Help screen!");
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
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
        backHoverImage.setMouseTransparent(true);
        backHoverImage.visibleProperty().bind(backButton.hoverProperty());
        backImage.visibleProperty().bind(backButton.hoverProperty().not());

        helpHoverImage.setMouseTransparent(true);
        helpHoverImage.visibleProperty().bind(helpButton.hoverProperty());
        helpImage.visibleProperty().bind(helpButton.hoverProperty().not());

        addHoverSound(helpButton);
        addHoverSound(backButton);
    }
}