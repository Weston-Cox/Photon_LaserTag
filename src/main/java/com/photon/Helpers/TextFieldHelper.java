package com.photon.Helpers;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TextFieldHelper {
    //*******************************************************************************************
    // applyNumericConstraint
    // Description: Applies a numeric constraint to the id column text fields. Doing this disallows
    //              the user from entering anything other than numbers.
    //*******************************************************************************************
    public static void applyNumericConstraint(TextField initialScreenTextField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        initialScreenTextField.setTextFormatter(textFormatter);
    }

    //*******************************************************************************************
	// applyTextConstraint
	// Description: Applies a text constraint to the codename column text fields. Doing this disallows
	//              the user from entering anything other than letters, numbers, spaces, periods, underscores, and hyphens.
	//*******************************************************************************************
	public static void applyTextConstraint(TextField initialScreenTextField) {
		TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
			if (change.getControlNewText().matches("^[a-zA-Z0-9\\s]*$")) {
				return change;
			}
			return null;
		});
		initialScreenTextField.setTextFormatter(textFormatter);
	}

    //*******************************************************************************************
	// copyOldTFProperties
	// Description: Copies the properties of an old TextField to a new TextField
	//*******************************************************************************************
	public static TextField copyOldTFProperties(TextField newTextField, TextField oldTextField) {
		newTextField.setId(oldTextField.getId());
		newTextField.setStyle(oldTextField.getStyle());
		newTextField.setPrefWidth(oldTextField.getPrefWidth());
		newTextField.setPrefHeight(oldTextField.getPrefHeight());
		newTextField.setMinWidth(oldTextField.getMinWidth());
		newTextField.setMinHeight(oldTextField.getMinHeight());
		newTextField.setMaxWidth(oldTextField.getMaxWidth());
		newTextField.setMaxHeight(oldTextField.getMaxHeight());
		newTextField.setLayoutX(oldTextField.getLayoutX());
		newTextField.setLayoutY(oldTextField.getLayoutY());
		newTextField.setAlignment(oldTextField.getAlignment());
		newTextField.setFocusTraversable(true);
		return newTextField;
	}
}
