package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
        volumeText.setText("tang giam am luong");
    }

    @FXML
    public void onFontButtonClick() {
        fontText.setText("thay doi phong chu");
    }

    @FXML
    public void onBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/main-menu-view.fxml"));
        Parent root = loader.load();

        backButton.getScene().setRoot(root);
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
    }
}
