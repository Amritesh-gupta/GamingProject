package Week4.service;

import Week4.model.Game;

import java.util.ArrayList;
import java.util.Optional;

public interface GameService {
    void addGame(Game game);
    void addAllGames(ArrayList<Game> games);
    Optional<Game> getGame(int GameID);
    Game getGameByName(String gameName);
    ArrayList<Game> getAllGames();
}
