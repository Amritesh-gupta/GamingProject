package Week4.service.Impl;

import Week4.model.Team;
import Week4.repository.TeamRepo;
import Week4.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepo teamRepo;

    public TeamServiceImpl(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Override
    public void addTeamGame(Team team) {
        teamRepo.save(team);
    }

    @Override
    public void addAllTeamGames(ArrayList<Team> games) {
        teamRepo.saveAll(games);
    }

    @Override
    public Optional<Team> getTeamGame(int teamID) {
        return teamRepo.findById(teamID);
    }

    @Override
    public ArrayList<Team> getAllTeams() {
        return (ArrayList<Team>) teamRepo.findAll();
    }

    @Override
    public ArrayList<Team> getAllTeamsByGameID(int gameID) {
        return (ArrayList<Team>) teamRepo.findByGameID(gameID);
    }
}
