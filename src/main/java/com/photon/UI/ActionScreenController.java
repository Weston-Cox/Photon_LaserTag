package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.Helpers.Player;
import com.photon.Helpers.GameTimer;

import java.io.IOException;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;
    private GameTimer gameTimer;

    @FXML
    private Label preGameTimerLabel; // The large pre-game timer label

    @FXML 
    private VBox playActionVbox; // The VBox that contains the play action buttons

    @FXML 
    private Label timerLabel; // 6 minute timer label

    @FXML
    private VBox greenTeamBox;

    @FXML
    private VBox redTeamBox;

    @FXML 
    private VBox playByPlayBox; // The VBox that contains the play by play

    @FXML
    public void initialize() {
        System.out.println("ActionScreenController initialized");
        // Initial styles when the screen is loaded
        preGameTimerLabel.setStyle("-fx-font-size: 48; -fx-font-weight: bold; -fx-text-fill: #ff0000;"); // Red pre-game timer
        timerLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #0077cc;"); // Blue in-game timer
    }
    
    // Called when F5 is pressed
    public void setActionScreenModel(ActionScreenModel actionScreenModel) {
        this.actionScreenModel = actionScreenModel;
        System.out.println("ActionScreenModel set in ActionScreenController");

        displayPlayers();
        startPreGameCountdown();
    }

    @FXML
    private void print() {
        this.actionScreenModel.printAllPlayers();
    }

    private void displayPlayers() {
        Player[] greenPlayers = actionScreenModel.getGreenPlayers();
        Player[] redPlayers = actionScreenModel.getRedPlayers();

        for (Player player : greenPlayers) {
            if (player != null) {
                greenTeamBox.getChildren().add(new Label(player.getCodename()));
            }
        }

        for (Player player : redPlayers) {
            if (player != null) {
                redTeamBox.getChildren().add(new Label(player.getCodename()));
            }
        }
    }

    // Called when F5 is pressed to start the pre-game countdown
    public void startPreGameCountdown() {
        preGameTimerLabel.setVisible(true); // Show the large pre-game timer label
        try {
            gameTimer = new GameTimer(new UDPClient());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameTimer.startCountdown();

        // Update the pre-game timer label every second
        new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
            int preGameTime = 30; // Pre-game countdown in seconds

            @Override
            public void run() {
                // Update the large pre-game timer label
                preGameTimerLabel.setText(String.valueOf(preGameTime));

                if (preGameTime <= 0) {
                    preGameTimerLabel.setVisible(false); // Hide the pre-game countdown timer
                    playActionVbox.setVisible(true); // Show the play action screen (if previously hidden)
                    startGameTimer(); // Start the 6-minute game timer
                    cancel();
                }

                preGameTime--;
            }
        }, 0, 1000); // Repeat every second
    }

    // Main game countdown timer (6 minutes)
    private void startGameTimer() {
        new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
            int timeRemaining = 360; // 6 minutes in seconds

            @Override
            public void run() {
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                String formattedTime = String.format("%02d:%02d", minutes, seconds);

                // Update the game timer label
                timerLabel.setText("Time Remaining: " + formattedTime);

                // End the game when time is up
                if (timeRemaining <= 0) {
                    System.out.println("Game Over!");
                    cancel();
                }

                timeRemaining--;
            }
        }, 0, 1000); // Repeat every second
    }
}