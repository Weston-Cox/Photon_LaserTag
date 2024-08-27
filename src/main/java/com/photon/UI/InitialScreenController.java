package com.photon.UI;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import com.photon.DB.Player;
import com.photon.DB.PlayerDAO;

public class InitialScreenController {
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, Integer> idColumn;
    @FXML
    private TableColumn<Player, String> codenameColumn;

    private PlayerDAO playerDAO;

    @FXML
    public void initialize() {
        this.playerDAO = new PlayerDAO();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codenameColumn.setCellValueFactory(new PropertyValueFactory<>("codename"));

        loadPlayerData();
    }

    private void loadPlayerData() {
        List<Player> players = this.playerDAO.getAllPlayers();
        playerTable.getItems().setAll(players);
    }
}
