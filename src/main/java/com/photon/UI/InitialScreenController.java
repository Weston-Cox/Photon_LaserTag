package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.photon.DB.PostgreSQL;
import com.photon.Models.InitialScreenModel;


public class InitialScreenController {
    private InitialScreenModel initialScreenModel;

    @FXML
    private TextField g_0_1, g_1_1, g_0_2, g_1_2, g_0_3, g_1_3, g_0_4, g_1_4, g_0_5, g_1_5, g_0_6, g_1_6, g_0_7, g_1_7, g_0_8, g_1_8, g_0_9, g_1_9, g_0_10, g_1_10, g_0_11, g_1_11, g_0_12, g_1_12, g_0_13, g_1_13, g_0_14, g_1_14, g_0_15, g_1_15;
    @FXML
    private TextField r_0_1, r_1_1, r_0_2, r_1_2, r_0_3, r_1_3, r_0_4, r_1_4, r_0_5, r_1_5, r_0_6, r_1_6, r_0_7, r_1_7, r_0_8, r_1_8, r_0_9, r_1_9, r_0_10, r_1_10, r_0_11, r_1_11, r_0_12, r_1_12, r_0_13, r_1_13, r_0_14, r_1_14, r_0_15, r_1_15;


    private Map<Integer, TextField[]> greenPlayers = new HashMap<>();
    private Map<Integer, TextField[]> redPlayers = new HashMap<>();

    public InitialScreenController(PostgreSQL postgreSQL) {
        this.initialScreenModel = new InitialScreenModel(postgreSQL); // Dependency Injection
    }

    //*******************************************************************************************
    // initialize
    // Description: Initializes the greenPlayers and redPlayers arrays and attaches focus lost listeners
    //*******************************************************************************************
    @FXML
    public void initialize() {
        // Initialize the greenPlayers array
        greenPlayers.put(1, new TextField[] {g_0_1, g_1_1});
        greenPlayers.put(2, new TextField[] {g_0_2, g_1_2});
        greenPlayers.put(3, new TextField[] {g_0_3, g_1_3});
        greenPlayers.put(4, new TextField[] {g_0_4, g_1_4});
        greenPlayers.put(5, new TextField[] {g_0_5, g_1_5});
        greenPlayers.put(6, new TextField[] {g_0_6, g_1_6});
        greenPlayers.put(7, new TextField[] {g_0_7, g_1_7});
        greenPlayers.put(8, new TextField[] {g_0_8, g_1_8});
        greenPlayers.put(9, new TextField[] {g_0_9, g_1_9});
        greenPlayers.put(10, new TextField[] {g_0_10, g_1_10});
        greenPlayers.put(11, new TextField[] {g_0_11, g_1_11});
        greenPlayers.put(12, new TextField[] {g_0_12, g_1_12});
        greenPlayers.put(13, new TextField[] {g_0_13, g_1_13});
        greenPlayers.put(14, new TextField[] {g_0_14, g_1_14});
        greenPlayers.put(15, new TextField[] {g_0_15, g_1_15});

        // Initialize the redPlayers array
        redPlayers.put(1, new TextField[] {r_0_1, r_1_1});
        redPlayers.put(2, new TextField[] {r_0_2, r_1_2});
        redPlayers.put(3, new TextField[] {r_0_3, r_1_3});
        redPlayers.put(4, new TextField[] {r_0_4, r_1_4});
        redPlayers.put(5, new TextField[] {r_0_5, r_1_5});
        redPlayers.put(6, new TextField[] {r_0_6, r_1_6});
        redPlayers.put(7, new TextField[] {r_0_7, r_1_7});
        redPlayers.put(8, new TextField[] {r_0_8, r_1_8});
        redPlayers.put(9, new TextField[] {r_0_9, r_1_9});
        redPlayers.put(10, new TextField[] {r_0_10, r_1_10});
        redPlayers.put(11, new TextField[] {r_0_11, r_1_11});
        redPlayers.put(12, new TextField[] {r_0_12, r_1_12});
        redPlayers.put(13, new TextField[] {r_0_13, r_1_13});
        redPlayers.put(14, new TextField[] {r_0_14, r_1_14});
        redPlayers.put(15, new TextField[] {r_0_15, r_1_15});


        attachFocusLostListeners(greenPlayers);
        attachFocusLostListeners(redPlayers);

    }

    //*******************************************************************************************
    // attachFocusLostListeners
    // Description: Loops through the player arrays and attaches focus lost listeners to the text fields
    //*******************************************************************************************
    private void attachFocusLostListeners(Map<Integer, TextField[]> players) {
        for (int i = 1; i <= players.size(); i++) {

            if (players.get(i)[0] == null || players.get(i)[1] == null) {continue;}

            final int row = i;

            applyNumericConstraint(players.get(row)[0]);  // Applies a numeric contraint to the ID column text fields
			applyTextConstraint(players.get(row)[1]); // Applies a text constraint to the codename column text fields            
            players.get(row)[0].focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) { // Focus lost
                        onFocusLost(players.get(row), row, 0); // Calls the onFocusLost method that we defined on the specifid TextField.
                    }
                }
            });

            players.get(row)[1].focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) { // Focus lost
                        onFocusLost(players.get(row), row, 1); // Calls the onFocusLost method that we defined on the specifid TextField.
                    }
                }
            });
        }  
    }


	private void attachFocusLostListeners(TextField[] textFieldRow, int row, String teamColor) {
		final int r = row;
		final int c = 1;
		TextField emptyField = new TextField(); // Empty text field to act as a placeholder
		emptyField.setId(teamColor);
		applyTextConstraint(textFieldRow[1]); // Applies a text constraint to the codename column text fields
		textFieldRow[1].focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) { // Focus lost
					onFocusLost(textFieldRow, r, c); // Calls the onFocusLost method that we defined on the specifid TextField.
				}
			}
		});
	}


    //*******************************************************************************************
    // onFocusLost
    // Description: Handles the focus lost event for a text field
	//*******************************************************************************************
	private void onFocusLost(TextField[] textFieldRow, int row, int column) {
		String safePattern = "^[a-zA-Z0-9\\s._-]*$"; // Safe pattern for codenames to prevent SQL injection
		String teamColor = textFieldRow[0].getId().substring(0, 1); // Gets the team color from the ID of the first text field

		if (column == 0 && textFieldRow[column].getText().isEmpty()) {
			System.out.println("ID is empty");
			return;
		} else if (column == 0) {
			int id = Integer.parseInt(textFieldRow[column].getText()); // Converts the string id to an integer
			String codename = initialScreenModel.getCodenameOfExistingID(id); // Grabs the codename from the database
			Platform.runLater(() -> { // Run on the JavaFX thread
				try {
					// If the text field is a green player
					if (teamColor.equals("g")) { 
						if (!codename.isEmpty() && textFieldRow[1].getText().isEmpty()) { // If the codename is not empty, set the codename to the one in the database
							// Create a new TextField
							TextField newTextField = new TextField(codename);
							// Copy properties from the old TextField
							newTextField.setId(textFieldRow[1].getId());
							newTextField.setStyle(textFieldRow[1].getStyle());
							newTextField.setPrefWidth(textFieldRow[1].getPrefWidth());
							newTextField.setPrefHeight(textFieldRow[1].getPrefHeight());
							newTextField.setMinWidth(textFieldRow[1].getMinWidth());
							newTextField.setMinHeight(textFieldRow[1].getMinHeight());
							newTextField.setMaxWidth(textFieldRow[1].getMaxWidth());
							newTextField.setMaxHeight(textFieldRow[1].getMaxHeight());
							newTextField.setLayoutX(textFieldRow[1].getLayoutX());
							newTextField.setLayoutY(textFieldRow[1].getLayoutY());
							newTextField.setAlignment(textFieldRow[1].getAlignment());
							newTextField.setFocusTraversable(true);
							// Replace the old TextField with the new one in the parent
							Parent parent = textFieldRow[1].getParent();
							if (parent instanceof GridPane) {
								GridPane gridPane = (GridPane) parent;
								int colIndex = GridPane.getColumnIndex(textFieldRow[1]);
								int rowIndex = GridPane.getRowIndex(textFieldRow[1]);
								// Find the index of the old TextField in the children list
								int oldIndex = gridPane.getChildren().indexOf(textFieldRow[1]);
								gridPane.getChildren().remove(textFieldRow[1]);
								// Add the new TextField at the same indexes
								gridPane.getChildren().add(oldIndex, newTextField);
								GridPane.setColumnIndex(newTextField, colIndex);
								GridPane.setRowIndex(newTextField, rowIndex);
								// Update the reference in the array
								textFieldRow[1] = newTextField; 
								// Set focus to the new TextField
								textFieldRow[1].requestFocus();
								attachFocusLostListeners(textFieldRow, row, "g");
							}
						}
						initialScreenModel.setIDOfGreenPlayer(row, column, id);
						initialScreenModel.setCodenameOfGreenPlayer(row, column, codename);
						initialScreenModel.storePlayer(id, codename); // Store/Update the player in the database

					// If the text field is a red player
					} else if (teamColor.equals("r")) { 
						if (!codename.isEmpty() && textFieldRow[1].getText().isEmpty()) { // If the codename is not empty, set the codename to the one in the database
							// Create a new TextField
							TextField newTextField = new TextField(codename);
							// Copy properties from the old TextField
							newTextField.setId(textFieldRow[1].getId());
							newTextField.setStyle(textFieldRow[1].getStyle());
							newTextField.setPrefWidth(textFieldRow[1].getPrefWidth());
							newTextField.setPrefHeight(textFieldRow[1].getPrefHeight());
							newTextField.setMinWidth(textFieldRow[1].getMinWidth());
							newTextField.setMinHeight(textFieldRow[1].getMinHeight());
							newTextField.setMaxWidth(textFieldRow[1].getMaxWidth());
							newTextField.setMaxHeight(textFieldRow[1].getMaxHeight());
							newTextField.setLayoutX(textFieldRow[1].getLayoutX());
							newTextField.setLayoutY(textFieldRow[1].getLayoutY());
							newTextField.setAlignment(textFieldRow[1].getAlignment());
							newTextField.setFocusTraversable(true);
							// Replace the old TextField with the new one in the parent
							Parent parent = textFieldRow[1].getParent();
							if (parent instanceof GridPane) {
								GridPane gridPane = (GridPane) parent;
								int colIndex = GridPane.getColumnIndex(textFieldRow[1]);
								int rowIndex = GridPane.getRowIndex(textFieldRow[1]);
								// Find the index of the old TextField in the children list
								int oldIndex = gridPane.getChildren().indexOf(textFieldRow[1]);
								gridPane.getChildren().remove(textFieldRow[1]);
								// Add the new TextField at the same indexes
								gridPane.getChildren().add(oldIndex, newTextField);
								GridPane.setColumnIndex(newTextField, colIndex);
								GridPane.setRowIndex(newTextField, rowIndex);
								// Update the reference in the array
								textFieldRow[1] = newTextField; 
								// Set focus to the new TextField
								textFieldRow[1].requestFocus();
								attachFocusLostListeners(textFieldRow, row, "r");
							}
						}
						initialScreenModel.setIDOfRedPlayer(row, column, id);
						initialScreenModel.setCodenameOfRedPlayer(row, column, codename);
						initialScreenModel.storePlayer(id, codename); // Store/Update the player in the database
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return;
		}
		if (column == 1 && textFieldRow[column].getText().isEmpty()) {
			System.out.println("Codename is empty");
			return;
		} else if (column == 1) {
			String inputCodename = textFieldRow[column].getText();
			System.out.println("Input codename: " + inputCodename);
			if (teamColor.equals("g")) {
				if (inputCodename.matches(safePattern)) {
					initialScreenModel.setCodenameOfGreenPlayer(row, column, inputCodename);
					if (!textFieldRow[0].getText().isEmpty()) { // If the ID text field is not empty, store the player
						initialScreenModel.storePlayer(Integer.parseInt(textFieldRow[0].getText()), inputCodename);
						return;
					}
				} else {
					textFieldRow[column].setText("");
					System.out.println("Unsafe green team input detected at [" + column + "][" + row + "]. Input cleared.");
				}
			} else if (teamColor.equals("r")) {
				if (inputCodename.matches(safePattern)) {
					initialScreenModel.setCodenameOfRedPlayer(row, column, inputCodename);
					if (!textFieldRow[0].getText().isEmpty()) { // If the ID text field is not empty, store the player
						initialScreenModel.storePlayer(Integer.parseInt(textFieldRow[0].getText()), inputCodename);
						return;
					}
				} else {
					textFieldRow[column].setText("");
					System.out.println("Unsafe red team input detected at [" + column + "][" + row + "]. Input cleared.");
				}
			}
		}
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


	//*******************************************************************************************
	// applyTextConstraint
	// Description: Applies a text constraint to the codename column text fields. Doing this disallows
	//              the user from entering anything other than letters, numbers, spaces, periods, underscores, and hyphens.
	//*******************************************************************************************
	private void applyTextConstraint(TextField initialScreenTextField) {
		TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
			if (change.getControlNewText().matches("^[a-zA-Z0-9\\s]*$")) {
				return change;
			}
			return null;
		});
		initialScreenTextField.setTextFormatter(textFormatter);
	}

    private void printInputtedPlayers(){
        System.out.println("Printing inputted players");
        // WILL BE IMPLEMENTED SOON
    }
}
