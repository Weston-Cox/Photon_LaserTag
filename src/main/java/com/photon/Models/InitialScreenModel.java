package com.photon.Models;

import java.io.IOException;

import com.photon.DB.PlayerDAO;
import com.photon.DB.UDPSocket;
import com.photon.Helpers.Player;

public class InitialScreenModel {
    private Player greenPlayers[][] = new Player[2][15];
    private Player redPlayers[][] = new Player[2][15];
    private PlayerDAO playerDAO;
    private UDPSocket udpSocket;


    public InitialScreenModel(PlayerDAO playerDAO, String udpAddress, int broadcastPort, int receivePort) {
        this.playerDAO = playerDAO;
        try {
            this.udpSocket = new UDPSocket(udpAddress, broadcastPort, receivePort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the greenPlayers and redPlayers arrays
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 15; j++) {
                greenPlayers[i][j] = new Player("", -1, "g");
                redPlayers[i][j] = new Player("", -1, "r");
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


    // Functions to communitcate with the server using the UPD socket to store and retrieve player information
    public boolean storePlayer(int id, String codename) {
        try {
            String message = "STORE " + id + " " + codename;
            udpSocket.broadcast(message);
            String response = udpSocket.receive();
            if ("SUCCESS".equals(response)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getIDByName(String name) {
        try {
            String message = "CHECK " + name;
            udpSocket.broadcast(message);
            String response = udpSocket.receive();
            if (!"NOT_FOUND".equals(response)) {
                return Integer.parseInt(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public boolean storePlayer(int id, String codename) {
    //     if (this.playerDAO.updatePlayerInfo(id, codename)) { // Trys to update the player info
    //         return true;
    //     } else { // If it can't update the player info, then it creates a new player
    //         return this.playerDAO.createPlayer(id, codename);
    //     }
    // }



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
}
