package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.photon.Helpers.TextFieldHelper;

public class EquipmentIDModalController {

    @FXML
    private TextField tfEquipmentID;

    private String equipmentID;

    private Stage dialogStage;
    private boolean submitClicked = false;

    @FXML
    private void initialize() {
        TextFieldHelper.applyNumericConstraint(tfEquipmentID);
        // Add an event listener for the Enter key.
        tfEquipmentID.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    submit();
                    break;
                default:
                    break;
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSubmitClicked() {
        return submitClicked;
    }

    @FXML
    private void submit() {
        this.equipmentID = tfEquipmentID.getText();
        if (this.equipmentID.isEmpty()){
            tfEquipmentID.getStyleClass().add("textfield-error");
            return;
        }
        submitClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public String getEquipmentID() {
        return this.equipmentID;
    }
}
