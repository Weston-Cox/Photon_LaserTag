package com.photon.Helpers;

public class Player {
    private String codename;
    private int id;
    private Team team;
    private int equipmentID;




    public Player(String codename, int id, String team, int equipmnetId) {
        this.codename = codename;
        this.id = id;
        this.team = Team.getTeam(team);
        this.equipmentID = equipmnetId;
    }


    public String getCodename() {
        return this.codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = Team.getTeam(team);
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
}
