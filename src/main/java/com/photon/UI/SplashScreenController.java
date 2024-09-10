package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashScreenController {

    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        // Load the logo image
        try {
            // Load the logo image
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
            logoImageView.setImage(logoImage);
        } catch (NullPointerException e) {
            System.err.println("Error: Logo image not found.");
            e.printStackTrace();
        }
    }
}
