package CricketGamingProjectWithSpringApplication;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class CricketTeam extends Team{

    private ArrayList<CricketPlayer> cricketTeam;

    @Override
    public ArrayList<? extends Player> getTeam() {
        return cricketTeam;
    }

    @JsonSetter("cricketPlayers")
    public void setPlayers(ArrayList<CricketPlayer> cricketPlayers) {
        players = cricketPlayers;
    }
    public void setCoaches(ArrayList<? extends Coach> cricketCoaches) {
        coaches = cricketCoaches;
    }

    @Override
    public void makeTeamOf(String cricketTeamName, Team team) {
        teamName = cricketTeamName;
        cricketTeam = new ArrayList<>();
        team.players.stream().filter(cricketPlayer -> cricketPlayer.getBelongsToCountry().compareTo(teamName) == 0)
                .forEach(player -> cricketTeam.add((CricketPlayer)player));

    }
}
