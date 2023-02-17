package Week4.service;

import Week4.model.BatsmanStat;
import Week4.model.CricketPlayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface BatsmanScorecardService {
    void addBatsmanStat(BatsmanStat batsmanStat);
    void updateBatsmanStat(BatsmanStat batsmanStat);
    BatsmanStat getBatsmanStat(int matchID, int playerID);

    ArrayList<BatsmanStat> getAllBatsmanStat(int matchID);
    LinkedHashMap<CricketPlayer,BatsmanStat> getMapOfBatsmanStat(int matchID, int teamID);
}
