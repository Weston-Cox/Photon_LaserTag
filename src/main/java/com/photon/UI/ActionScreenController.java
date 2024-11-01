package com.photon.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.UDP.UDPServer;
import com.photon.Helpers.CountdownCallback;
import com.photon.Helpers.GameTimer;
import com.photon.Helpers.Player;

import java.io.IOException;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;
    private GameTimer gameTimer;
    private UDPClient udpClient;
    private UDPServer udpServer;

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


    @FXML 
    private HBox actionScreenContent; // The HBox that contains the main content

    @FXML
    private HBox timerBox; // The HBox that contains the timer

    @FXML
    public void initialize() {
        // Initial styles when the screen is loaded
        preGameTimerLabel.setStyle("-fx-font-size: 38; -fx-font-weight: bold; -fx-text-fill: #ff0000;"); // Red pre-game timer
        timerLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #0077cc;"); // Blue in-game timer



        splitPaneHorizontal.setManaged(false);
        splitPaneVertical.setManaged(false);
        textFlowPane.setManaged(false);
    }

    public void setUDPServer(UDPServer udpServer) {
        this.udpServer = udpServer;
        System.out.println("UDP Server set in ActionScreenController");

        startUdpServer();
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


    public void startUdpServer() {
        if (this.udpServer == null) {
            System.out.println("UDP Server is null");
            return;
        }

        this.udpServer.setCallback( message -> {
            //TODO Handle the received message (parse, process, etc.)
            System.out.println("Message received: " + message);

            Platform.runLater(() ->{
                //TODO Update the UI
            });
        });
        // new Thread(udpServer).start();
    }

    private void displayPlayers() {
        Player[] greenPlayers = actionScreenModel.getGreenPlayers();
        Player[] redPlayers = actionScreenModel.getRedPlayers();

        for (Player player : greenPlayers) {
            if (player != null && player.getCodename() != "") {
                GridPane playerGrid = new GridPane();
                playerGrid.setPrefWidth(300); // Set a preferred width for the GridPane

                // Set column constraints
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setHgrow(Priority.ALWAYS);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setHgrow(Priority.NEVER);
                playerGrid.getColumnConstraints().addAll(col1, col2);

                Label playerLabel = new Label(player.getCodename());
                Label playerScoreLabel = new Label(String.valueOf(player.getScore()));
                playerLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: green; -fx-font-family: 'Arial';");
                playerScoreLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: green; -fx-font-family: 'Arial';");

                // Add labels to the GridPane
                playerGrid.add(playerLabel, 0, 0);
                playerGrid.add(playerScoreLabel, 1, 0);

                // Add the GridPane to the VBox
                greenTeamBox.getChildren().add(playerGrid);
            }
        }

        for (Player player : redPlayers) {
            if (player != null && player.getCodename() != "") {
                GridPane playerGrid = new GridPane();
                playerGrid.setPrefWidth(300); // Set a preferred width for the GridPane

                // Set column constraints
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setHgrow(Priority.ALWAYS);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setHgrow(Priority.NEVER);
                playerGrid.getColumnConstraints().addAll(col1, col2);

                Label playerLabel = new Label(player.getCodename());
                Label playerScoreLabel = new Label(String.valueOf(player.getScore()));
                playerLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: red; -fx-font-family: 'Arial';");
                playerScoreLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: red; -fx-font-family: 'Arial';");

                // Add labels to the GridPane
                playerGrid.add(playerLabel, 0, 0);
                playerGrid.add(playerScoreLabel, 1, 0);

                // Add the GridPane to the VBox
                redTeamBox.getChildren().add(playerGrid);
            }
        }
    }

    // Called when F5 is pressed to start the pre-game countdown
    public void startPreGameCountdown() {
        preGameTimerLabel.setVisible(true); // Show the large pre-game timer label
        gameTimer.startPreGameCountdown(4, preGameTimerLabel, new CountdownCallback() {
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