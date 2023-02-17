package Week4.service.Impl;

import Week4.model.Game;
import Week4.repository.GameRepo;
import Week4.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class GameServiceImpl implements GameService {

    private GameRepo gameRepo;

    public GameServiceImpl(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    @Override
    public void addGame(Game game) {
        gameRepo.save(game);
    }

    @Override
    public void addAllGames(ArrayList<Game> games) {
        gameRepo.saveAll(games);
    }

    @Override
    public Optional<Game> getGame(int gameID) {
        return gameRepo.findById(gameID);
    }

    @Override
    public Game getGameByName(String gameName) {
        return gameRepo.findByName(gameName);
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return (ArrayList<Game>) gameRepo.findAll();
    }
}
