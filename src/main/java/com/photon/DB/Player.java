package com.photon.DB;

import com.photon.Helpers.Team;

public class Player {
    private String codename;
    private int id;
    private Team team;

    public Player(String codename, int id, String team) {
        this.codename = codename;
        this.id = id;
        this.team = Team.getTeam(team);
    }


    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = Team.getTeam(team);
    }
}
