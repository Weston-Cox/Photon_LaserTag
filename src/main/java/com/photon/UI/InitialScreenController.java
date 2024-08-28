package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import com.photon.DB.Player;
import com.photon.DB.PlayerDAO;
import com.photon.DB.PostgreSQL;

public class InitialScreenController {
    private PostgreSQL postgreSQL;
    private PlayerDAO playerDAO;


    @FXML
    private TextField greenPlayers[][] = new TextField[2][15];
    @FXML
    private TextField redPlayers[][] = new TextField[2][15];

    public InitialScreenController(PostgreSQL postgreSQL) {
        this.postgreSQL = postgreSQL; // Dependency Injection
        this.playerDAO = new PlayerDAO(this.postgreSQL);

    }

    @FXML
    public void initialize() {
        // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // codenameColumn.setCellValueFactory(new PropertyValueFactory<>("codename"));

        // loadPlayerData();
        printInputtedPlayers();

    }

    // private void loadPlayerData() {
    //     List<Player> players = this.playerDAO.getAllPlayers();
    //     playerTable.getItems().setAll(players);
    // }

    private void printInputtedPlayers(){
        System.out.println("Printing inputted players");
        // WILL BE IMPLEMENTED SOON
    }
}
