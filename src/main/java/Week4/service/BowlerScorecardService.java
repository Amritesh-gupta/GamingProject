package Week4.service;

import Week4.model.BatsmanStat;
import Week4.model.BowlerStat;
import Week4.model.CricketPlayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface BowlerScorecardService {
    void addBowlerStat(BowlerStat bowlerStat);
    void updateBowlerStat(BowlerStat bowlerStat);
    BowlerStat getBowlerStat(int matchID, int playerID);
    ArrayList<BowlerStat> getAllBowlerStat(int matchID);
    LinkedHashMap<CricketPlayer,BowlerStat> getMapOfBowlerStat(int matchID, int teamID);
}
