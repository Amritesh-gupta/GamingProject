package CricketGamingProject;

import java.util.ArrayList;

public abstract class Team {
    protected String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    protected ArrayList<? extends Player> players;

    protected ArrayList<? extends Coach> coaches;

    public ArrayList<? extends Player> getPlayers() {
        return players;
    }

    public ArrayList<? extends Coach> getCoaches() {
        return coaches;
    }

    public abstract void makeTeamOf(String teamName, Team team);

    public abstract ArrayList<? extends Player> getTeam();

}
