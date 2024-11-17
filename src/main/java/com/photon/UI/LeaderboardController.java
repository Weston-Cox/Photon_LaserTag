package com.photon.UI;

import java.io.IOException;

import com.photon.Helpers.Player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaderboardController {

    @FXML
    private VBox greenTeamLeaderboard;

    @FXML
    private VBox redTeamLeaderboard;

    @FXML
    private Button resetButton;

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

    @FXML
    private void resetGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/photon/InitialScreen.fxml"));
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