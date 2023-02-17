package Week4.service;

import Week4.model.CricketPlayer;

import java.util.ArrayList;
import java.util.Optional;

public interface CricketPlayerService {
    void addPlayer(CricketPlayer player);
    void addAllPlayers(ArrayList<CricketPlayer> players);
    ArrayList<CricketPlayer> getAllCricketPlayers();
    Optional<CricketPlayer> getCricketPlayerByID(int playerID);
    ArrayList<CricketPlayer> getAllCricketPlayersByTeamID(int teamID);
}
