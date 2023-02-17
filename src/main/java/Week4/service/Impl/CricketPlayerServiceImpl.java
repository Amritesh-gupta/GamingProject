package Week4.service.Impl;

import Week4.model.CricketPlayer;
import Week4.repository.CricketPlayerRepo;
import Week4.service.CricketPlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CricketPlayerServiceImpl implements CricketPlayerService {

    private CricketPlayerRepo cricketPlayerRepo;

    public CricketPlayerServiceImpl(CricketPlayerRepo cricketPlayerRepo) {
        this.cricketPlayerRepo = cricketPlayerRepo;
    }

    @Override
    public void addPlayer(CricketPlayer player) {
        cricketPlayerRepo.save(player);
    }

    @Override
    public void addAllPlayers(ArrayList<CricketPlayer> players) {
        cricketPlayerRepo.saveAll(players);
    }

    @Override
    public ArrayList<CricketPlayer> getAllCricketPlayers() {
        return (ArrayList<CricketPlayer>) cricketPlayerRepo.findAll();
    }

    @Override
    public Optional<CricketPlayer> getCricketPlayerByID(int playerID) {
        return cricketPlayerRepo.findById(playerID);
    }

    @Override
    public ArrayList<CricketPlayer> getAllCricketPlayersByTeamID(int teamID) {
        return (ArrayList<CricketPlayer>) cricketPlayerRepo.findByTeamID(teamID);
    }

}
