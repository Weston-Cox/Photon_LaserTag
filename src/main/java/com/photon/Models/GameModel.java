package com.photon.Models;

import java.io.IOException;

import com.photon.DB.PlayerDAO;
import com.photon.DB.PostgreSQL;
import com.photon.Helpers.Player;
import com.photon.UDP.UDPClient;

public class GameModel {
    protected Player greenPlayers[] = new Player[16];
    protected Player redPlayers[] = new Player[16];
    protected PlayerDAO playerDAO;
    protected UDPClient udpClient;

    public GameModel(PostgreSQL postgreSQL) {
        this.playerDAO = new PlayerDAO(postgreSQL);
        try {
            this.udpClient = new UDPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize the greenPlayers and redPlayers arrays
        for (int j = 0; j < 16; j++) {
            greenPlayers[j] = new Player("", -1, "g", -1);
            redPlayers[j] = new Player("", -1, "r", -1);
        }
    }


    public void printAllPlayers() {
        System.out.println("Green Players: \n");
        for (int i = 1; i < 16; i++) {
            System.out.println("ID: " + greenPlayers[i].getId() + " Codename: " + greenPlayers[i].getCodename() + " Equipment ID: " + greenPlayers[i].getEquipmentID());
        }
        System.out.println("Red Players: \n");
        for (int i = 1; i < 16; i++) {
            System.out.println("ID: " + redPlayers[i].getId() + " Codename: " + redPlayers[i].getCodename() + " Equipment ID: " + redPlayers[i].getEquipmentID());
        }
    }


    public void clearAllPlayers() {
        for (int i = 1; i < 16; i++) {
            greenPlayers[i].setId(-1);
            greenPlayers[i].setCodename("");
            greenPlayers[i].setEquipmentID(-1);
            redPlayers[i].setId(-1);
            redPlayers[i].setCodename("");
            redPlayers[i].setEquipmentID(-1);
        }
    }


    //*******************************************************************************************
    //! GETTERS AND SETTERS
    //*******************************************************************************************
    public PostgreSQL getPostgreSQL() {
        return playerDAO.getPostgreSQL();
    }

    public Player[] getGreenPlayers() {
        return greenPlayers;
    }

    public void setGreenPlayers(Player[] greenPlayers) {
        this.greenPlayers = greenPlayers;
    }

    public Player[] getRedPlayers() {
        return redPlayers;
    }

    public void setRedPlayers(Player[] redPlayers) {
        this.redPlayers = redPlayers;
    }

    public void setCodenameOfGreenPlayer(int row, String codename) {
        greenPlayers[row].setCodename(codename);
    }

    public void setCodenameOfRedPlayer(int row, String codename) {
        redPlayers[row].setCodename(codename);
    }

    public void setIDOfGreenPlayer(int row, int id) {
        greenPlayers[row].setId(id);
    }

    public void setIDOfRedPlayer(int row, int id) {
        redPlayers[row].setId(id);
    }

    public void setEquipmentIDOfGreenPlayer(int row, int equipmentID) {
        greenPlayers[row].setEquipmentID(equipmentID);
        try {
            udpClient.send(equipmentID + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEquipmentIDOfRedPlayer(int row, int equipmentID) {
        redPlayers[row].setEquipmentID(equipmentID);
        try {
            udpClient.send(equipmentID + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCodenameOfGreenPlayer(int row) {
        return greenPlayers[row].getCodename();
    }

    public String getCodenameOfRedPlayer(int row) {
        return redPlayers[row].getCodename();
    }

    public int getIDOfGreenPlayer(int row) {
        return greenPlayers[row].getId();
    }

    public int getIDOfRedPlayer(int row) {
        return redPlayers[row].getId();
    }

    public int getEquipmentIDOfGreenplayer(int row) {
        return greenPlayers[row].getEquipmentID();
    }

    public int getEquipmentIDOfRedPlayer(int row) {
        return redPlayers[row].getEquipmentID();
    }

}
