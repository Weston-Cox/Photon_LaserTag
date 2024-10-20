package com.photon.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.Helpers.GameTimer;
import com.photon.UDP.UDPClient;
import com.photon.Helpers.Player;
import com.photon.Helpers.GameTimer;

import java.io.IOException;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;
    private GameTimer gameTimer;
    private UDPClient udpClient;

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

    public ActionScreenController() {
    }

    public ActionScreenController(GameTimer gameTimer, UDPClient udpClient) {
        this.gameTimer = gameTimer;
        this.udpClient = udpClient;
    }

    @FXML 
    private HBox actionScreenContent; // The HBox that contains the main content

    @FXML
    private HBox timerBox; // The HBox that contains the timer

    @FXML
    public void initialize() {
        System.out.println("ActionScreenController initialized");
        // Initial styles when the screen is loaded
        preGameTimerLabel.setStyle("-fx-font-size: 48; -fx-font-weight: bold; -fx-text-fill: #ff0000;"); // Red pre-game timer
        timerLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #0077cc;"); // Blue in-game timer
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public void setUDPClient(UDPClient udpClient) {
        this.udpClient = udpClient;
    }
    
    // Called when F5 is pressed
    public void setActionScreenModel(ActionScreenModel actionScreenModel) {
        this.actionScreenModel = actionScreenModel;
        System.out.println("ActionScreenModel set in ActionScreenController");

        displayPlayers();
        startPreGameCountdown();
    }

    private void displayPlayers() {
        Player[] greenPlayers = actionScreenModel.getGreenPlayers();
        Player[] redPlayers = actionScreenModel.getRedPlayers();

        for (Player player : greenPlayers) {
            if (player != null) {
                Label playerLabel = new Label(player.getCodename());
                playerLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: green; -fx-font-family: 'Arial';");
                greenTeamBox.getChildren().add(playerLabel);
            }
        }

        for (Player player : redPlayers) {
            if (player != null) {
                Label playerLabel = new Label(player.getCodename());
                playerLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: red; -fx-font-family: 'Arial';");
                redTeamBox.getChildren().add(playerLabel);
            }
        }
    }

    // Called when F5 is pressed to start the pre-game countdown
    public void startPreGameCountdown() {
        preGameTimerLabel.setVisible(true); // Show the large pre-game timer label
        this.gameTimer.startCountdown();
    
        // Update the pre-game timer label every second
        new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
            int preGameTime = 30; // Pre-game countdown in seconds
    
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Update the large pre-game timer label
                    preGameTimerLabel.setText(String.valueOf(preGameTime));
    
                    if (preGameTime <= 0) {
                        preGameTimerLabel.setVisible(false); // Hide the pre-game countdown timer
                        actionScreenContent.setVisible(true); // Show the play action screen (if previously hidden)
                        timerBox.setVisible(true); // Show the timer
                        startGameTimer(); // Start the 6-minute game timer
                        cancel();
                    }
                });
    
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
                Platform.runLater(() -> {
                    int minutes = timeRemaining / 60;
                    int seconds = timeRemaining % 60;
                    String formattedTime = String.format("%02d:%02d", minutes, seconds);

                // Update the game timer label
                Platform.runLater(() -> {
                    timerLabel.setText("Time Remaining: " + formattedTime);
                });

                    // End the game when time is up
                    if (timeRemaining <= 0) {
                        System.out.println("Game Over!");
                        cancel();
                    }
                });

                timeRemaining--;
            }
        }, 0, 1000); // Repeat every second
    }
}