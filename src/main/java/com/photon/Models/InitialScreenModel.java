package com.photon.Models;

import com.photon.DB.PostgreSQL;
import com.photon.Helpers.Player;

public class InitialScreenModel extends GameModel {

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

    //*******************************************************************************************
    // storePlayer
    // Description: Stores the player in the database when given the player's ID and codename
    //*******************************************************************************************
    public boolean storePlayer(int id, String codename) {
        if (this.playerDAO.updatePlayerInfo(id, codename)) { // Trys to update the player info
            return true;
        } else { // If it can't update the player info, then it creates a new player
            return this.playerDAO.createPlayer(id, codename);
        }
    }

}