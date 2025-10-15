package org.example.arkanoid.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelpController {
    private boolean isTransisioning = false;
    @FXML
    private Button helpButton;

    @FXML
    private Label helpLabel;

    @FXML
    private Button backButton;

    @FXML
    private void onHelpButton() {
        helpLabel.setText("Help screen!");
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/main-menu-view.fxml"));
        Parent newRoot = loader.load();
        helpButton.getScene().setRoot(newRoot);
    }

}
