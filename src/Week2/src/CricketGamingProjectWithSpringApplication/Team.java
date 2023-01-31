package CricketGamingProjectWithSpringApplication;

import java.util.ArrayList;

public class Team {
    private ArrayList<CricketPlayer> cricketPlayers;

    public ArrayList<CricketPlayer> getCricketPlayers() {
        return cricketPlayers;
    }

    public void setCricketPlayers(ArrayList<CricketPlayer> cricketPlayers) {
        this.cricketPlayers = cricketPlayers;
    }

    public void setCoaches(ArrayList<Coach> coaches) {
        this.coaches = coaches;
    }

    private ArrayList<Coach> coaches;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    private String teamName;

    public Team(){
        cricketPlayers = new ArrayList<>();
        coaches = new ArrayList<>();
    }
    public void addPlayer(CricketPlayer player){
        cricketPlayers.add(player);
    }


    public void addCoach(Coach coach){
        coaches.add(coach);
    }

    public ArrayList<Coach> getCoaches(){
        return coaches;
    }
}
