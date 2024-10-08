package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.photon.Models.ActionScreenModel;
import com.photon.Helpers.Player;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;

    @FXML
    private VBox greenTeamBox;

    @FXML
    private VBox redTeamBox;

    public void setActionScreenModel(ActionScreenModel actionScreenModel) {
        this.actionScreenModel = actionScreenModel;
        displayPlayers();
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
}