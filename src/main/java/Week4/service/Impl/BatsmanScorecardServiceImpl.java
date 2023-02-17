package Week4.service.Impl;

import Week4.model.BatsmanStat;
import Week4.model.CricketPlayer;
import Week4.repository.BatsmanStatRepo;
import Week4.service.BatsmanScorecardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class BatsmanScorecardServiceImpl implements BatsmanScorecardService {

    private BatsmanStatRepo batsmanStatRepo;

    public BatsmanScorecardServiceImpl(BatsmanStatRepo batsmanStatRepo) {
        this.batsmanStatRepo = batsmanStatRepo;
    }

    @Override
    public void addBatsmanStat(BatsmanStat batsmanStat) {
        batsmanStatRepo.save(batsmanStat);
    }

    @Override
    public void updateBatsmanStat(BatsmanStat batsmanStat) {
        int runs = batsmanStat.getRuns();
        int balls = batsmanStat.getBallsPlayed();
        int sixes = batsmanStat.getNumberOfSixes();
        int fours = batsmanStat.getNumberOfFours();
        int matchID = batsmanStat.getMatchID().getMatchID();
        int playerID = batsmanStat.getPlayerID().getPlayerID();

        batsmanStatRepo.updateBatsmanStat(runs,balls,sixes,fours,matchID,playerID);
    }

    @Override
    public BatsmanStat getBatsmanStat(int matchID, int playerID) {
        return batsmanStatRepo.findByPlayerIDAndMatchID(playerID,matchID);
    }

    @Override
    public ArrayList<BatsmanStat> getAllBatsmanStat(int matchID) {
        return batsmanStatRepo.findByMatchID(matchID);
    }

    @Override
    public LinkedHashMap<CricketPlayer, BatsmanStat> getMapOfBatsmanStat(int matchID, int teamID) {
        return null;
    }
}
