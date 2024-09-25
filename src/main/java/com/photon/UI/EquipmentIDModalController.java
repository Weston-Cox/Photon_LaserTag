package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipmentIDModalController {

    @FXML
    private TextField tfEquipmentID;

    private Stage dialogStage;
    private boolean submitClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitClicked() {
        return submitClicked;
    }

    @FXML
    private void submit() {
        submitClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public String getEquipmentID() {
        return tfEquipmentID.getText();
    }
}
