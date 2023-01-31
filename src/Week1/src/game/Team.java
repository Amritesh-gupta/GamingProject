package game;

import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players;
    private ArrayList<Coach> coaches;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    private String teamName;

    public Team(){
        players = new ArrayList<>();
        coaches = new ArrayList<>();
    }
    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void addCoach(Coach coach){
        coaches.add(coach);
    }

    public ArrayList<Coach> getCoaches(){
        return coaches;
    }
}
