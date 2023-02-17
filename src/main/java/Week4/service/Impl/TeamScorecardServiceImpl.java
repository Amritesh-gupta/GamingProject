package Week4.service.Impl;

import Week4.model.TeamStat;
import Week4.repository.TeamStatRepo;
import Week4.service.TeamScorecardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class TeamScorecardServiceImpl implements TeamScorecardService {

    private TeamStatRepo teamStatRepo;

    public TeamScorecardServiceImpl(TeamStatRepo teamStatRepo) {
        this.teamStatRepo = teamStatRepo;
    }

    @Override
    public void addTeamStat(TeamStat teamStat) {
        teamStatRepo.save(teamStat);
    }

    @Override
    public void addAllTeamsStat(ArrayList<TeamStat> teamStats) {
        teamStatRepo.saveAll(teamStats);
    }

    @Override
    public TeamStat getTeamStat(int matchID, int teamID) {
        return teamStatRepo.findByTeamIDAndMatchID(teamID, matchID);
    }

    @Override
    public ArrayList<TeamStat> getAllTeamsStatByMatch(int matchID) {
        return teamStatRepo.findByMatchID(matchID);
    }


}
