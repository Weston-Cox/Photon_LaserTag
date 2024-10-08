package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.photon.Models.ActionScreenModel;
import com.photon.Helpers.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.IOException;

import com.photon.App;

public class ActionScreenController {

    @FXML
    private VBox greenTeamBox;

    @FXML
    private VBox redTeamBox;

    private ActionScreenModel actionScreenModel;
    private Timeline countdownTimeline; // Timeline for the countdown
    private Label countdownLabel; // Label for the countdown

    public void setActionScreenModel(ActionScreenModel actionScreenModel) {
        this.actionScreenModel = actionScreenModel;
    }

    @FXML
    public void initialize() {
        loadPlayers();
    }

    private void loadPlayers() { // Loads the players into the green and red team boxes
        Player[] greenPlayers = actionScreenModel.getGreenPlayers(); // Gets the green players
        Player[] redPlayers = actionScreenModel.getRedPlayers(); // Gets the red players

        for (Player player : greenPlayers) { // Loops through the green players
            if (player != null && player.getId() != -1) { // If the player is not null and has an ID
                greenTeamBox.getChildren().add(new Label(player.getCodename())); // Adds the player's codename to the green team box
            }
        }

        for (Player player : redPlayers) { // Loops through the red players
            if (player != null && player.getId() != -1) { // If the player is not null and has an ID
                redTeamBox.getChildren().add(new Label(player.getCodename())); // Adds the player's codename to the red team box
            }
        }
    }

    @FXML
    private void switchToPrimary() { // Switches to the initial screen
        try { 
            App.setRoot("InitialScreen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGameCountdown() { // Starts the game countdown
        // Start the game countdown
            countdownTimeline = new Timeline();
            countdownTimeline.setCycleCount(Timeline.INDEFINITE);
            countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
                int currentTime = Integer.parseInt(countdownLabel.getText());
                if (currentTime > 0) {
                    countdownLabel.setText(String.valueOf(currentTime - 1));
                } else {
                    countdownTimeline.stop();
                    // Logic to start the game
                    System.out.println("Game started!");
                }
            }));
            countdownLabel.setText("10"); // Set initial countdown time
            countdownTimeline.playFromStart();
        }
    }