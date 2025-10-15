package org.example.arkanoid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.arkanoid.config.Constants;

import java.io.IOException;

public class HelpController {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setRoot(root);
    }
}
