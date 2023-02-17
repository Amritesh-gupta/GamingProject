package Week4.service;

import Week4.model.Game;
import Week4.model.TeamStat;

import java.util.ArrayList;
import java.util.Optional;

public interface TeamScorecardService {
    void addTeamStat(TeamStat teamStat);
    void addAllTeamsStat(ArrayList<TeamStat> teamStats);
    TeamStat getTeamStat(int matchID, int teamID);
    ArrayList<TeamStat> getAllTeamsStatByMatch(int matchID);
}
