package Week4.service.Impl;

import Week4.model.BowlerStat;
import Week4.model.CricketPlayer;
import Week4.repository.BowlerStatRepo;
import Week4.service.BowlerScorecardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class BowlerScorecardServiceImpl implements BowlerScorecardService {

    private BowlerStatRepo bowlerStatRepo;

    public BowlerScorecardServiceImpl(BowlerStatRepo bowlerStatRepo) {
        this.bowlerStatRepo = bowlerStatRepo;
    }

    @Override
    public void addBowlerStat(BowlerStat bowlerStat) {
        bowlerStatRepo.save(bowlerStat);
    }

    @Override
    public void updateBowlerStat(BowlerStat bowlerStat) {
        double overs = bowlerStat.getNumberOfOvers();
        int maidenOvers = bowlerStat.getNumberOfMaidenOvers();
        int runs = bowlerStat.getRunsCost();
        int wickets = bowlerStat.getNumberOfWickets();
        int matchID = bowlerStat.getMatchID().getMatchID();
        int playerID = bowlerStat.getPlayerID().getPlayerID();

        bowlerStatRepo.updateBowlerStat(overs,maidenOvers,runs,wickets,matchID,playerID);
    }

    @Override
    public BowlerStat getBowlerStat(int matchID, int playerID) {
        return bowlerStatRepo.findByPlayerIDAndMatchID(playerID,matchID);
    }

    @Override
    public ArrayList<BowlerStat> getAllBowlerStat(int matchID) {
        return bowlerStatRepo.findByMatchID(matchID);
    }

    @Override
    public LinkedHashMap<CricketPlayer, BowlerStat> getMapOfBowlerStat(int matchID, int teamID) {
        return null;
    }
}
