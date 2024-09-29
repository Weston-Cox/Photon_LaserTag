package com.photon.UI;

import java.io.IOException;
import javafx.fxml.FXML;
// Photon Specific Packages
import com.photon.App;
import com.photon.Models.ActionScreenModel;

public class ActionScreenController {

    private ActionScreenModel actionScreenModel;

    public void setActionScreenModel(ActionScreenModel actionScreenModel) {
        this.actionScreenModel = actionScreenModel;
    }

    @FXML
    private void print() {
        this.actionScreenModel.printAllPlayers();
    }
}