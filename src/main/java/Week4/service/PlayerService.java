package Week4.service;

import Week4.model.Player;

import java.util.ArrayList;
import java.util.Optional;

public interface PlayerService {
    void addPlayer(Player player);
    void addAllPlayers(ArrayList<Player> players);
    Optional<Player> getPlayerByID(int playerID);
    ArrayList<Player> getAllPlayers();
    ArrayList<Player> getAllPlayersByCountry(String country);

}
