package com.photon.Models;

import java.io.IOException;

import com.photon.DB.PlayerDAO;
import com.photon.DB.PostgreSQL;
import com.photon.Helpers.Player;
import com.photon.UDP.UDPClient;

public class InitialScreenModel extends GameModel {
    // private Player greenPlayers[] = new Player[16];
    // private Player redPlayers[] = new Player[16];
    // private PlayerDAO playerDAO;
    // private UDPClient udpClient;


    public InitialScreenModel(PostgreSQL postgreSQL) {
        super(postgreSQL);
    }


    //*******************************************************************************************
    // getCodenameOfExisitingID
    // Description: Returns the codename of a player with a given ID
    //*******************************************************************************************
    public String getCodenameOfExistingID(int id) {
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

    // public void printAllPlayers() {
    //     System.out.println("Green Players: \n");
    //     for (int i = 1; i < 16; i++) {
    //         System.out.println("ID: " + greenPlayers[i].getId() + " Codename: " + greenPlayers[i].getCodename() + " Equipment ID: " + greenPlayers[i].getEquipmentID());
    //     }
    //     System.out.println("Red Players: \n");
    //     for (int i = 1; i < 16; i++) {
    //         System.out.println("ID: " + redPlayers[i].getId() + " Codename: " + redPlayers[i].getCodename() + " Equipment ID: " + redPlayers[i].getEquipmentID());
    //     }
    // }

// //*******************************************************************************************
// //! GETTERS AND SETTERS
// //*******************************************************************************************
//     public Player[] getGreenPlayers() {
//         return greenPlayers;
//     }

//     public void setGreenPlayers(Player[] greenPlayers) {
//         this.greenPlayers = greenPlayers;
//     }

//     public Player[] getRedPlayers() {
//         return redPlayers;
//     }

//     public void setRedPlayers(Player[] redPlayers) {
//         this.redPlayers = redPlayers;
//     }

//     public void setCodenameOfGreenPlayer(int row, String codename) {
//         greenPlayers[row].setCodename(codename);
//     }

//     public void setCodenameOfRedPlayer(int row, String codename) {
//         redPlayers[row].setCodename(codename);
//     }

//     public void setIDOfGreenPlayer(int row, int id) {
//         greenPlayers[row].setId(id);
//     }

//     public void setIDOfRedPlayer(int row, int id) {
//         redPlayers[row].setId(id);
//     }

//     public void setEquipmentIDOfGreenPlayer(int row, int equipmentID) {
//         greenPlayers[row].setEquipmentID(equipmentID);
//         try {
//             udpClient.send(equipmentID + "");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void setEquipmentIDOfRedPlayer(int row, int equipmentID) {
//         redPlayers[row].setEquipmentID(equipmentID);
//         try {
//             udpClient.send(equipmentID + "");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public String getCodenameOfGreenPlayer(int row) {
//         return greenPlayers[row].getCodename();
//     }

//     public String getCodenameOfRedPlayer(int row) {
//         return redPlayers[row].getCodename();
//     }

//     public int getIDOfGreenPlayer(int row) {
//         return greenPlayers[row].getId();
//     }

//     public int getIDOfRedPlayer(int row) {
//         return redPlayers[row].getId();
//     }

//     public int getEquipmentIDOfGreenplayer(int row) {
//         return greenPlayers[row].getEquipmentID();
//     }

//     public int getEquipmentIDOfRedPlayer(int row) {
//         return redPlayers[row].getEquipmentID();
//     }
}