package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class EquipmentIDModalController {

    @FXML
    private TextField tfEquipmentID;

    private String equipmentID;

    private Stage dialogStage;
    private boolean submitClicked = false;

    @FXML
    private void initialize() {
        applyNumericConstraint(tfEquipmentID);
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

    //*******************************************************************************************
    // applyNumericConstraint
    // Description: Applies a numeric constraint to the id column text fields. Doing this disallows
    //              the user from entering anything other than numbers.
    //*******************************************************************************************
    private void applyNumericConstraint(TextField initialScreenTextField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        initialScreenTextField.setTextFormatter(textFormatter);
    }
}
