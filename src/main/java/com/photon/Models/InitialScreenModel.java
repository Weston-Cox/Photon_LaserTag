package com.photon.Models;

import java.io.IOException;

import com.photon.DB.PlayerDAO;
import com.photon.DB.PostgreSQL;
import com.photon.Helpers.Player;
import com.photon.UDP.UDPClient;

public class InitialScreenModel {
    private Player greenPlayers[][] = new Player[2][15];
    private Player redPlayers[][] = new Player[2][15];
    private PlayerDAO playerDAO;
    private UDPClient udpClient;


    public InitialScreenModel(PostgreSQL postgreSQL) {
        this.playerDAO = new PlayerDAO(postgreSQL);
        try {
            this.udpClient = new UDPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize the greenPlayers and redPlayers arrays
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 15; j++) {
                greenPlayers[i][j] = new Player("", -1, "g", -1);
                redPlayers[i][j] = new Player("", -1, "r", -1);
            }
        }
    }


    //*******************************************************************************************
    // getCodenameOfExisitingID
    // Description: Returns the codename of a player with a given ID
    //*******************************************************************************************
    public String getCodenameOfExistingID(int id) {
        System.out.println("ID: " + id + " DEBUGGING");
        Player player = this.playerDAO.findPlayerByID(id);
        if (player != null) {
            return player.getCodename();
        }
        return "";
    }


    public boolean storePlayer(int id, String codename) {
        if (this.playerDAO.updatePlayerInfo(id, codename)) { // Trys to update the player info
            return true;
        } else { // If it can't update the player info, then it creates a new player
            return this.playerDAO.createPlayer(id, codename);
        }
    }



//*******************************************************************************************
//! GETTERS AND SETTERS
//*******************************************************************************************
    public Player[][] getGreenPlayers() {
        return greenPlayers;
    }

    public void setGreenPlayers(Player[][] greenPlayers) {
        this.greenPlayers = greenPlayers;
    }

    public Player[][] getRedPlayers() {
        return redPlayers;
    }

    public void setRedPlayers(Player[][] redPlayers) {
        this.redPlayers = redPlayers;
    }

    public void setCodenameOfGreenPlayer(int row, int col, String codename) {
        greenPlayers[col][row].setCodename(codename);
    }

    public void setCodenameOfRedPlayer(int row, int col, String codename) {
        redPlayers[col][row].setCodename(codename);
    }

    public void setIDOfGreenPlayer(int row, int col, int id) {
        greenPlayers[col][row].setId(id);
    }

    public void setIDOfRedPlayer(int row, int col, int id) {
        redPlayers[col][row].setId(id);
    }

    public void setEquipmentIDOfGreenPlayer(int row, int col, int equipmentID) {
        greenPlayers[col][row].setEquipmentID(equipmentID);
        try {
            udpClient.send(equipmentID + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEquipmentIDOfRedPlayer(int row, int col, int equipmentID) {
        redPlayers[col][row].setEquipmentID(equipmentID);
        try {
            udpClient.send(equipmentID + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCodenameOfGreenPlayer(int row, int col) {
        return greenPlayers[col][row].getCodename();
    }

    public String getCodenameOfRedPlayer(int row, int col) {
        return redPlayers[col][row].getCodename();
    }

    public int getIDOfGreenPlayer(int row, int col) {
        return greenPlayers[col][row].getId();
    }

    public int getIDOfRedPlayer(int row, int col) {
        return redPlayers[col][row].getId();
    }

    public int getEquipmentIDOfGreenplayer(int row, int col) {
        return greenPlayers[col][row].getEquipmentID();
    }

    public int getEquipmentIDOfRedPlayer(int row, int col) {
        return redPlayers[col][row].getEquipmentID();
    }
}