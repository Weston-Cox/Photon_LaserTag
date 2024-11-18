package com.photon.UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.UDP.UDPServer;
import com.photon.Helpers.CountdownCallback;
import com.photon.Helpers.GameTimer;
import com.photon.Helpers.Player;
import com.photon.Helpers.Team;

import java.io.IOException;
import java.util.Random;

import javafx.util.Duration;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;
    private GameTimer gameTimer;
    private UDPClient udpClient;
    private UDPServer udpServer;
    private Timeline greenTeamFlashingTimeline;
    private Timeline redTeamFlashingTimeline; 

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
    private Label greenTeamScore; // Green team score label

    @FXML
    private Label redTeamScore; // Red team score label

    @FXML
    private VBox greenTeamBox;

    @FXML
    private VBox redTeamBox;

    @FXML 
    private TextFlow playByPlayTextFlow; 

     @FXML
    private ScrollPane scrollPane; // The ScrollPane for the TextFlow

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

            runningGameLogic(message);

        });
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
        gameTimer.startPreGameCountdown(20, preGameTimerLabel, new CountdownCallback() {
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
        // chooseRandomTrack();
        // Sends the game start signal to the server
            try {
                udpClient.send("202");
            } catch (IOException e) {
                e.printStackTrace();
            }

        splitPaneHorizontal.setManaged(true);
        splitPaneVertical.setManaged(true);
        textFlowPane.setManaged(true);
        gameTimer.startGameCountdown(361, timerLabel, new CountdownCallback() {
            @Override
            public void onCountdownFinished() { // Transmit the game over signal to the server three times
                System.out.println("Game Over");
                for (int i = 0; i < 3; i++) {
                    try {
                        udpClient.send("221");
                        System.out.println("Game Over signal sent to server");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                showLeaderboardScreen();
            }
        });
    }

    private void showLeaderboardScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/photon/LeaderboardScreen.fxml"));
            Parent leaderboardScreen = loader.load();
            LeaderboardController controller = loader.getController();
            controller.setPlayers(actionScreenModel.getGreenPlayers(), actionScreenModel.getRedPlayers());
            Scene scene = new Scene(leaderboardScreen, 900, 720);
            Stage stage = (Stage) timerLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Leaderboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runningGameLogic(String message) {
        Player attackingPlayer = actionScreenModel.findPlayerByEquipmentID(Integer.parseInt(message.split(":")[0]));
        Player defendingPlayer = actionScreenModel.findPlayerByEquipmentID(Integer.parseInt(message.split(":")[1]));
        String hitPlayerID = "-1";

        if (message.split(":")[1].equals("53") && attackingPlayer.getTeam() == Team.GREEN) { // Green player has hit the red base
            if (attackingPlayer.getHitBase()) {
                return; // If the player has already hit the base, do not allow them to hit it again
            }
            String playByPlayMessage = "Red base has been hit by "+attackingPlayer.getCodename();
            System.out.println(playByPlayMessage);
            appendPlayByPlayMessage(playByPlayMessage);
            hitPlayerID = "53";
            attackingPlayer.setScore(attackingPlayer.getScore() + 100);
            attackingPlayer.setHitBase(true);
            actionScreenModel.setGreenScore(actionScreenModel.getGreenScore() + 100);

        } else if (message.split(":")[1].equals("43") && attackingPlayer.getTeam() == Team.RED) { // 53 is the equipment ID for the green base
            if (attackingPlayer.getHitBase()) {
                return; // If the player has already hit the base, do not allow them to hit it again
            }
            String playByPlayMessage = "Green base has been hit by " + attackingPlayer.getCodename();
            System.out.println(playByPlayMessage);
            appendPlayByPlayMessage(playByPlayMessage);
            hitPlayerID = "43";
            attackingPlayer.setScore(attackingPlayer.getScore() + 100);
            attackingPlayer.setHitBase(true);
            actionScreenModel.setRedScore(actionScreenModel.getRedScore() + 100);

        } else if(defendingPlayer != null && attackingPlayer.getTeam() == defendingPlayer.getTeam()) { // Player has hit a teammate
            String playByPlayMessage = "The attacking player (" + attackingPlayer.getCodename() + ") has hit a teammate (" + defendingPlayer.getCodename() + ")";
            System.out.println(playByPlayMessage);
            appendPlayByPlayMessage(playByPlayMessage);
            hitPlayerID = attackingPlayer.getEquipmentID()+""; // If a player hits a teammate, return their own equipment ID
            attackingPlayer.setScore(attackingPlayer.getScore() - 10);

            if (attackingPlayer.getTeam() == Team.GREEN) {
                actionScreenModel.setGreenScore(actionScreenModel.getGreenScore() - 10);
            } else {
                actionScreenModel.setRedScore(actionScreenModel.getRedScore() - 10);
            }

        } else if (defendingPlayer != null){ // Player has been hit by an opponent
            String playByPlayMessage = "The attacking player (" + attackingPlayer.getCodename() + ") has hit a teammate (" + defendingPlayer.getCodename() + ")";
            System.out.println(playByPlayMessage);
            appendPlayByPlayMessage(playByPlayMessage);
            hitPlayerID = defendingPlayer.getEquipmentID()+"";
            attackingPlayer.setScore(attackingPlayer.getScore() + 10);

            if (attackingPlayer.getTeam() == Team.GREEN) {
                actionScreenModel.setGreenScore(actionScreenModel.getGreenScore() + 10);
            } else {
                actionScreenModel.setRedScore(actionScreenModel.getRedScore() + 10);
            }

        }

        sendUDPReceivedReceipt(hitPlayerID); // Send a receipt to the server that the message has been received

        Platform.runLater(() ->{
            // Update the player scores
            for (Player player : actionScreenModel.getGreenPlayers()) {
                if (player != null && player.getCodename() != "") {
                    for (int i = 0; i < greenTeamBox.getChildren().size(); i++) {
                        GridPane playerGrid = (GridPane) greenTeamBox.getChildren().get(i);
                        Label playerNameLabel = (Label) playerGrid.getChildren().get(0);
                        Label playerScoreLabel = (Label) playerGrid.getChildren().get(1);
                        if (player.getCodename().equals(playerNameLabel.getText())) {

                            if (player.getHitBase()) {
                                playerNameLabel.setText("B | " + playerNameLabel.getText());
                                playerNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: gold; -fx-font-family: 'Arial Black';");
                            }

                            playerScoreLabel.setText(String.valueOf(player.getScore()));
                        }
                    }
                }
            }

            for (Player player : actionScreenModel.getRedPlayers()) {
                if (player != null && player.getCodename() != "") {
                    for (int i = 0; i < redTeamBox.getChildren().size(); i++) {
                        GridPane playerGrid = (GridPane) redTeamBox.getChildren().get(i);
                        Label playerNameLabel = (Label) playerGrid.getChildren().get(0);
                        Label playerScoreLabel = (Label) playerGrid.getChildren().get(1);
                        if (player.getCodename().equals(playerNameLabel.getText())) {

                            if (player.getHitBase()) {
                                playerNameLabel.setText("B | " + playerNameLabel.getText());
                                playerNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: gold; -fx-font-family: 'Arial Black';");
                            }

                            playerScoreLabel.setText(String.valueOf(player.getScore()));
                        }
                    }
                }
            }

            // Update the team scores
            greenTeamScore.setText("Green Team: " + String.valueOf(actionScreenModel.getGreenScore()));
            redTeamScore.setText("Red Team: " + String.valueOf(actionScreenModel.getRedScore()));

            // Update the flashing effect based on the team scores
            updateFlashingEffect();
        });

    }

    private void appendPlayByPlayMessage(String message) {
        Platform.runLater(() -> {
            Text text = new Text(message + "\n");
            text.setStyle("-fx-fill: white; -fx-font-size: 14;");
            playByPlayTextFlow.getChildren().add(text);

            // Scroll to the bottom of the text flow
            scrollPane.layout();
            scrollPane.setVvalue(1.0);
        });
    }

    private void sendUDPReceivedReceipt(String hitPlayerID) {
        try {
            udpClient.send(hitPlayerID);
        } catch (IOException e) {
            System.out.println("Error sending message to server: " + e.getMessage());
        }
    }

        // Method to start the flashing effect
    private void startFlashing(Label label) {

        stopFlashing(label);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> label.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-font-family: 'Arial Black'; -fx-text-fill: yellow;")),
            new KeyFrame(Duration.seconds(1), e -> label.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-font-family: 'Arial Black'; -fx-text-fill: white;"))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        if (label == greenTeamScore) {
            greenTeamFlashingTimeline = timeline;
        } else if (label == redTeamScore) {
            redTeamFlashingTimeline = timeline;
        }
    }

    // Method to stop the flashing effect
    private void stopFlashing(Label label) {
        if (label == greenTeamScore && greenTeamFlashingTimeline != null) {
            greenTeamFlashingTimeline.stop();
            greenTeamFlashingTimeline = null;
            label.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-font-family: 'Arial Black'; -fx-text-fill: white;");
        } else if (label == redTeamScore && redTeamFlashingTimeline != null) {
            redTeamFlashingTimeline.stop();
            redTeamFlashingTimeline = null;
            label.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-font-family: 'Arial Black'; -fx-text-fill: white;");
        }
    }

    // Method to update the flashing effect based on the team scores
    private void updateFlashingEffect() {
        int greenScore = actionScreenModel.getGreenScore();
        int redScore = actionScreenModel.getRedScore();

        if (greenScore > redScore) {
            startFlashing(greenTeamScore);
            stopFlashing(redTeamScore);
        } else if (redScore > greenScore) {
            startFlashing(redTeamScore);
            stopFlashing(greenTeamScore);
        } else {
            stopFlashing(greenTeamScore);
            stopFlashing(redTeamScore);
        }
    }

    // Method to randomly choose track to play
    private void chooseRandomTrack() {
        Random rand = new Random();
        int randNum = rand.nextInt(1,8);
        AudioClip gameSound = new AudioClip(getClass().getResource("/tracks/Track0" + randNum + ".mp3").toString());
        gameSound.play();
    }

}