package Week4.service.Impl;

import Week4.model.Match;
import Week4.repository.MatchRepo;
import Week4.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepo matchRepo;

    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    public Match addMatch(Match match) {
        return matchRepo.save(match);
    }

    @Override
    public void addAllMatches(ArrayList<Match> matches) {
        matchRepo.saveAll(matches);
    }


    @Override
    public void addWinnerIDToMatch(int matchID, int winnerID) {
        matchRepo.updateWinnerID(winnerID,matchID);
    }

    @Override
    public Optional<Match> getMatch(int matchID) {
        return matchRepo.findById(matchID);
    }
}
