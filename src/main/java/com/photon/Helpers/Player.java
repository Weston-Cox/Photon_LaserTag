package com.photon.Helpers;

public class Player {
    private String codename;
    private int id;
    private Team team;
    private int equipmentID;
    private int score;
    private boolean hitBase;




    public Player(String codename, int id, String team, int equipmentID) {
        this.codename = codename;
        this.id = id;
        this.team = Team.getTeam(team);
        this.equipmentID = equipmentID;
        this.score = 0;
        this.hitBase = false;
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

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getHitBase() {
        return this.hitBase;
    }

    public void setHitBase(boolean hitBase) {
        this.hitBase = hitBase;
    }
}
