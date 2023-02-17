package Week4.service;

import Week4.model.Team;

import java.util.ArrayList;
import java.util.Optional;

public interface TeamService {
    void addTeamGame(Team team);
    void addAllTeamGames(ArrayList<Team> games);
    Optional<Team> getTeamGame(int TeamID);
    ArrayList<Team> getAllTeams();
    ArrayList<Team> getAllTeamsByGameID(int gameID);
}
