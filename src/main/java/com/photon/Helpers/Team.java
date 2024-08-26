package com.photon.Helpers;

public enum Team {
    RED("r"),
    GREEN("g"),
    UNNASSIGNED("u");

    private final String team;

    Team(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public static Team getTeam(String team) {
        switch (team) {
            case "r":
                return RED;
            case "g":
                return GREEN;
            default:
                return UNNASSIGNED;
        }
    }
}
