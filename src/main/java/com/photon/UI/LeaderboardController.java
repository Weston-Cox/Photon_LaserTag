package com.photon.UI;

import java.io.IOException;
import java.util.Random;

import com.photon.Helpers.GameTimer;
import com.photon.Helpers.Player;
import com.photon.Models.ActionScreenModel;
import com.photon.UDP.UDPClient;
import com.photon.UDP.UDPServer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LeaderboardController {

    @FXML
    private VBox greenTeamLeaderboard;

    @FXML
    private VBox redTeamLeaderboard;

    @FXML
    private Button resetButton;

    private ActionScreenModel actionScreenModel;
    private UDPClient udpClient;
    private UDPServer udpServer;
    private GameTimer gameTimer;
    private MediaPlayer gameSound;

    public void setPlayers(Player[] greenPlayers, Player[] redPlayers) {
        displayPlayers(greenPlayers, greenTeamLeaderboard, "green");
        displayPlayers(redPlayers, redTeamLeaderboard, "red");
    }

    private void displayPlayers(Player[] players, VBox teamBox, String color) {
        for (Player player : players) {
            if (player != null && !player.getCodename().isEmpty()) {
                Label playerLabel = new Label(player.getCodename() + ": " + player.getScore());
                playerLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: " + color + "; -fx-font-family: 'Arial';");
                teamBox.getChildren().add(playerLabel);
            }
        }
    }

    public void setDependencies(ActionScreenModel actionScreenModel, UDPClient udpClient, UDPServer udpServer, GameTimer gameTimer) {
        this.actionScreenModel = actionScreenModel;
        this.udpClient = udpClient;
        this.udpServer = udpServer;
        this.gameTimer = new GameTimer();

        // Create the MediaPlayer instance
        Random rand = new Random();
        int randNum = rand.nextInt(1, 8);
        Media media = new Media(getClass().getResource("/tracks/Track0" + randNum + ".mp3").toString());
        gameSound = new MediaPlayer(media);
        this.gameTimer.setMediaPlayer(gameSound);
    }

    @FXML
    private void resetGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/photon/InitialScreen.fxml"));
            loader.setControllerFactory(param -> {
                if (param == InitialScreenController.class) {
                    return new InitialScreenController(
                        actionScreenModel.getPostgreSQL(),
                        udpClient,
                        udpServer,
                        gameTimer
                    );
                } else {
                    try {
                        return param.getConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Parent initialScreen = loader.load();
            Scene scene = new Scene(initialScreen, 900, 720);
            Stage stage = (Stage) resetButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Initial Screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}