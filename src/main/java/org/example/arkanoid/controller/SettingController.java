package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class SettingController {
    @FXML
    private Button volumeButton;

    @FXML
    private Button fontButton;

    @FXML
    private Button backButton;

    @FXML
    private Label volumeText;

    @FXML
    private Label fontText;

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
}
