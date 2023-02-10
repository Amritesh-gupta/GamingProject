package models;

import java.util.ArrayList;

public abstract class Team {
    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    protected ArrayList<? extends Player> players;

    private int teamID;

    public void setPlayers(ArrayList<? extends Player> players) {
        this.players = players;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }

    public ArrayList<? extends Player> getPlayers() {
        return players;
    }

    protected ArrayList<? extends Coach> coaches;



    public abstract ArrayList<? extends Player> getTeam();

}
