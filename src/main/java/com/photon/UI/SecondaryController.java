package com.photon.UI;

import java.io.IOException;
import javafx.fxml.FXML;
// Photon Specific Packages
import com.photon.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}