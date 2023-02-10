package models;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class CricketTeam extends Team{

    private ArrayList<CricketPlayer> cricketTeam;

    @Override
    public ArrayList<? extends Player> getTeam() {
        return cricketTeam;
    }

    @JsonSetter("CricketPlayers")
    public void setCricketPlayers(ArrayList<CricketPlayer> cricketPlayers) {
        players = cricketPlayers;
    }
    public void setCoaches(ArrayList<? extends Coach> cricketCoaches) {
        coaches = cricketCoaches;
    }

    public void setCricketTeam(ArrayList<CricketPlayer> cricketTeam) {
        this.cricketTeam = cricketTeam;
    }


}
