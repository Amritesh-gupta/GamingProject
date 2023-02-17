package Week4.service.Impl;

import Week4.model.Player;
import Week4.repository.PlayersRepo;
import Week4.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayersRepo playersRepo;

    public PlayerServiceImpl(PlayersRepo playersRepo) {
        this.playersRepo = playersRepo;
    }

    @Override
    public void addPlayer(Player player) {
        playersRepo.save(player);
    }

    @Override
    public void addAllPlayers(ArrayList<Player> players) {
        playersRepo.saveAll(players);
    }

    @Override
    public Optional<Player> getPlayerByID(int playerID) {
        return playersRepo.findById(playerID);
    }

    @Override
    public ArrayList<Player> getAllPlayers() {
        return (ArrayList<Player>) playersRepo.findAll();
    }

    @Override
    public ArrayList<Player> getAllPlayersByCountry(String country) {
        return (ArrayList<Player>) playersRepo.findByCountry(country);
    }

}
