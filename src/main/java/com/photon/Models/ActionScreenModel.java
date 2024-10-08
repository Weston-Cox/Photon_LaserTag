package com.photon.Models;

import com.photon.DB.PostgreSQL;
import com.photon.Helpers.Player;

public class ActionScreenModel extends GameModel {

    private Player[] greenPlayers;
    private Player[] redPlayers;

    public ActionScreenModel(PostgreSQL postgreSQL) {
        super(postgreSQL);
    }

    public void setGreenPlayers(Player[] greenPlayers) {
        this.greenPlayers = greenPlayers;
    }

    public Player[] getGreenPlayers() {
        return greenPlayers;
    }

    public void setRedPlayers(Player[] redPlayers) {
        this.redPlayers = redPlayers;
    }

    public Player[] getRedPlayers() {
        return redPlayers;
    }

    public void printAllPlayers() {
        System.out.println("Green Team:");
        for (Player player : greenPlayers) {
            if (player != null) {
                System.out.println(player.getCodename());
            }
        }
        System.out.println("Red Team:");
        for (Player player : redPlayers) {
            if (player != null) {
                System.out.println(player.getCodename());
            }
        }
    }
}