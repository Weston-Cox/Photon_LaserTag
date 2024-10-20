package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.Helpers.CountdownCallback;
import com.photon.Helpers.GameTimer;
import com.photon.Helpers.Player;

import java.io.IOException;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;
    private GameTimer gameTimer;
    private UDPClient udpClient;

    @FXML
    private SplitPane splitPaneVertical; // The main split pane

    @FXML
    private SplitPane splitPaneHorizontal; // The horizontal split pane

    @FXML
    private Pane textFlowPane; // The pane that contains the text flow

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

    // Empty constructor for JavaFX Platform
    public ActionScreenController() {
    }

    public ActionScreenController(UDPClient udpClient, GameTimer gameTimer) {
        this.udpClient = udpClient;
        this.gameTimer = gameTimer;
    }

    @FXML 
    private HBox actionScreenContent; // The HBox that contains the main content

    @FXML
    private HBox timerBox; // The HBox that contains the timer

    @FXML
    public void initialize() {
        System.out.println("ActionScreenController initialized");
        // Initial styles when the screen is loaded
        preGameTimerLabel.setStyle("-fx-font-size: 38; -fx-font-weight: bold; -fx-text-fill: #ff0000;"); // Red pre-game timer
        timerLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #0077cc;"); // Blue in-game timer

        splitPaneHorizontal.setManaged(false);
        splitPaneVertical.setManaged(false);
        textFlowPane.setManaged(false);
    }

    public void setUDPClient(UDPClient udpClient) {
        this.udpClient = udpClient;
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
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
                playerLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: green; -fx-font-family: 'Arial';");
                greenTeamBox.getChildren().add(playerLabel);
            }
        }

        for (Player player : redPlayers) {
            if (player != null) {
                Label playerLabel = new Label(player.getCodename());
                playerLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: red; -fx-font-family: 'Arial';");
                redTeamBox.getChildren().add(playerLabel);
            }
        }
    }

    // Called when F5 is pressed to start the pre-game countdown
    public void startPreGameCountdown() {
        preGameTimerLabel.setVisible(true); // Show the large pre-game timer label
        gameTimer.startPreGameCountdown(5, preGameTimerLabel, new CountdownCallback() {
            @Override
            public void onCountdownFinished() {
                preGameTimerLabel.setVisible(false); // Hide the large pre-game timer label
                preGameTimerLabel.setManaged(false); // Hide the large pre-game timer label
                actionScreenContent.setVisible(true); // Show the main content
                timerBox.setVisible(true); // Show the timer box
                startGameTimer();
            }
        });
        
    }    

    // Main game countdown timer (6 minutes)
    private void startGameTimer() {
        // Sends the game start signal to the server 3 times
        for (int i = 0; i < 3; i++) {
            try {
                udpClient.send("202");
                System.out.println("Game Has Started");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        splitPaneHorizontal.setManaged(true);
        splitPaneVertical.setManaged(true);
        textFlowPane.setManaged(true);
        gameTimer.startGameCountdown(361, timerLabel, new CountdownCallback() {
            @Override
            public void onCountdownFinished() {
                System.out.println("Game Over");
                try {
                    udpClient.send("221");
                    System.out.println("Game Over signal sent to server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}